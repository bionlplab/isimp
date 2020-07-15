"""
Usage:
    generate <input_file> <output_file>
"""
import copy
import json
import logging
import re

import docopt


# member-collection
# member
# collection
# hypernymy
# hyponym

def repl_space(s, begin, end):
    return s[:begin] + ' ' * (end - begin) + s[end:]


def repl_text(s, target, begin, end):
    return s[: begin] + target[begin: end] + s[end:]


def process_parenthesis(sentence, simp):
    sentences = []
    begin = sentence['FROM']
    for comp in simp['COMP']:
        if comp['TYPE'] in ('referred noun phrase', 'parenthesized elements'):
            s = copy.deepcopy(sentence)
            text = s['TEXT']
            text = repl_space(text, simp['FROM'] - begin, simp['TO'] - begin + 1)
            text = repl_text(text, sentence['TEXT'], comp['FROM'] - begin, comp['TO'] - begin)
            s['TEXT'] = text
            sentences.append(s)
        else:
            raise KeyError(comp['TYPE'])
    return sentences


def process_apposition(sentence, simp):
    sentences = []
    begin = sentence['FROM']
    for comp in simp['COMP']:
        if comp['TYPE'] in ('referred noun phrase', 'appositive'):
            s = copy.deepcopy(sentence)
            text = s['TEXT']
            text = repl_space(text, simp['FROM'] - begin, simp['TO'] - begin)
            text = repl_text(text, sentence['TEXT'], comp['FROM'] - begin, comp['TO'] - begin)
            s['TEXT'] = text
            sentences.append(s)
        else:
            raise KeyError(comp['TYPE'])
    return sentences


def process_coordination(sentence, simp):
    sentences = []
    begin = sentence['FROM']
    for comp in simp['COMP']:
        if comp['TYPE'] in ('conjunct', ):
            s = copy.deepcopy(sentence)
            text = s['TEXT']
            text = repl_space(text, simp['FROM'] - begin, simp['TO'] - begin + 1)
            text = repl_text(text, sentence['TEXT'], comp['FROM'] - begin, comp['TO'] - begin)
            s['TEXT'] = text
            sentences.append(s)
        elif comp['TYPE'] in ('conjunction', ):
            pass
        else:
            raise KeyError(comp['TYPE'])
    return sentences


def process_relative(sentence, simp):
    sentences = []
    begin = sentence['FROM']
    np = None
    for comp in simp['COMP']:
        if comp['TYPE'] in ('referred noun phrase', ):
            np = comp
            s = copy.deepcopy(sentence)
            text = s['TEXT']
            text = repl_space(text, simp['FROM'] - begin, simp['TO'] - begin + 1)
            text = repl_text(text, sentence['TEXT'], comp['FROM'] - begin, comp['TO'] - begin)
            s['TEXT'] = text
            sentences.append(s)
        elif comp['TYPE'] in ('clause', ):
            s = copy.deepcopy(sentence)
            text = ' ' * len(s['TEXT'])
            text = repl_text(text, sentence['TEXT'], comp['FROM'] - begin, comp['TO'] - begin)
            text = repl_text(text, sentence['TEXT'], np['FROM'] - begin, np['TO'] - begin)
            s['TEXT'] = text
            sentences.append(s)
        else:
            raise KeyError(comp['TYPE'])
    return sentences


def simplify_helper(sentence, total_simp_sentences):
    if len(sentence['SIMP']) == 0:
        # print('from', sentence['id'], 'get', sentence['id'], sentence['TEXT'])
        total_simp_sentences.append(sentence)
        return
    simp_idx = len(sentence['SIMP'])
    simp = sentence['SIMP'].pop(-1)
    if simp['TYPE'] == 'parenthesis':
        ss = process_parenthesis(sentence, simp)
    elif 'relative' in simp['TYPE']:
        ss = process_relative(sentence, simp)
    elif 'coordination' in simp['TYPE']:
        ss = process_coordination(sentence, simp)
    elif simp['TYPE'] == 'member-collection':
        ss = [sentence]
    elif simp['TYPE'] == 'hypernymy':
        ss = [sentence]
    elif simp['TYPE'] == 'apposition':
        ss = process_apposition(sentence, simp)
    else:
        logging.warning('Cannot parse type: %s', simp['TYPE'])
        ss = [sentence]
    for i, s in enumerate(ss):
        s['id'] = s['id'] + f'_{simp_idx}/{i}'
        # print('from', sentence['id'], 'get', s['id'], s['TEXT'])
        simplify_helper(s, total_simp_sentences)


def generate_sentences(src, dest):
    with open(src, encoding='utf8') as fin, open(dest, 'w', encoding='utf8') as fout:
        for i, line in enumerate(fin):
            sentence = json.loads(line)
            sentence['id'] = str(i)
            total_sentences = []
            simplify_helper(sentence, total_sentences)
            sentences_text = set(s['TEXT'] for s in total_sentences)
            for s in sentences_text:
                s = re.sub('\\s+', ' ', s)
                s = re.sub('\\n', ' ', s)
                s = s.strip().capitalize()
                if len(s) == 0:
                    continue
                if s[-1] != '.':
                    s += '.'
                fout.write(s + '\n')
            fout.write('\n')


def main():
    args = docopt.docopt(__doc__)
    generate_sentences(args['<input_file>'], args['<output_file>'])


if __name__ == '__main__':
    main()
    # dir = Path(r'Y:\Subjects\isimp\testcases')
    # main()
    # generate_sentences(dir / 'foo_out.json', dir / 'foo_xxx.txt')

"""
Usage:
    docx2text <input_file> <output_file>
"""

import os
from pathlib import Path
from docx import Document
import docopt


def docx2text(src, dest):
    doc = Document(str(src))
    text = []
    for para in doc.paragraphs:
        text.append(para.text)
    with open(dest, 'w', encoding='utf8') as fp:
        for s in text:
            fp.write(s + '\n')


if __name__ == '__main__':
    args = docopt.docopt(__doc__)
    docx2text(args['<input_file>'], args['<output_file>'])
![iSimp](https://github.com/yfpeng/isimp/blob/master/isimplogo3.png?raw=true)

-----------------------

![HitCount](https://hits.dwyl.com/yfpeng/isimp.svg)

Sentence simplification is a technique designed to detect the various types of clauses and constructs used in a
complex sentence, in an effort to produce two or more simple sentences while maintaining both coherence and the
communicated message. By reducing the syntactic complexity of a sentence, the goal of sentence simplification is
to ease the development of natural language processing and text mining tools. For this purpose, we developed **iSimp**.

To illustrate the usefulness of sentence simplification, consider the following complex sentence from the biomedical
literature:

> Active Raf-1 phosphorylates and activates the mitogen-activated protein (MAP) kinase/extracellular signal-regulated
> kinase kinase 1 (MEK1), which in turn phosphorylates and activates the MAP kinases/extracellular signal regulated
> kinases, ERK1 and ERK2. (PMID-8557975)

The major syntactic constructs that we consider when simplifying a sentence are: coordinations, relative clauses,
appositions, parenthesized elements, and introductory phrases.

After identifying these constructs, the complex sentence can be broken into multiple simple sentences. Here we show
only six examples, which require combining two coordinations with the relative clause and the apposition:

1.  Active Raf-2 phosphorylates MEK1
2.  MEK1 phosphorylates ERK1
3.  MEK1 phosphorylates ERK2
4.  Active Raf-2 activates MEK1
5.  MEK1 activates ERK1
6.  MEK1 activates ERK2

Suppose we used a straightforward rule "phosphorylates X" to extract the theme of phosphorylation relation in the
text, where X is a protein appears as a head word. This rule is able to match straightforward mentions of
phosphorylation in text. However, it will fail to find mentions of phosphorylation in the above complex sentence.
But the rule can now apply to (a)-(c) and extract "MEK1", "ERK1", and "ERK2" as themes.

## Prerequisites

* python >=3.6
* Linux

## Getting the source code

You can clone the repository

```bash
$ git clone https://github.com/yfpeng/isimp
$ cd isimp
```

## Build Java

Downdload [lib](https://github.com/yfpeng/isimp/releases/download/v1.0.4/lib.zip) and put them in the `lib` folder.

```bash
$ bash scripts/build.sh
```

## Set up Python

Once you have a copy of the source, you can prepare a virtual environment

```bash
$ export PYTHONPATH=.
$ virtualenv --python=/usr/bin/python3.6 env
$ source env/bin/activate
$ pip install -r requirements.txt
```

**Note**: If `virtualenv` is not installed, try ``pip install virtualenv``.

## Detect simplification constructs

```bash
bash scripts/simplify.sh foo.txt foo_isimp.jsonl
```

## Generate simplified sentences

```bash
python scripts/generate_sentences.py foo_isimp.jsonl foo_isimp.txt
```

## BioCreative IV Track 1

To make iSimp readily usable in NLP and text mining tools, we participate in [BioCreative IV Track 1](http://www.biocreative.org/tasks/biocreative-iv/track-1-interoperability/),
and adopt the [BioC](http://www.ncbi.nlm.nih.gov/CBBresearch/Dogan/BioC/) format, a simple XML format to share text
documents and annotations. Our contribution in this track includes:

* The development of Java BioC IO API, which is independent of the particular XML parser used. The Java API developed
as part of the iSimp project becomes part of the public release of the BioC package.
* A tag set for annotating simplification constructs. The tag set can be used in any sentence simplification system
to exchange data with other NLP systems and, thus, make the system easily interoperable with other NLP applications.
* A unique mechanism allowing new artificial text to be included and treated as if it were an original collection in
the following processing.
* The construction of an iSimp corpus, provided in the BioC format. In addition to this corpus, we also adapted
the BioC format to the GENIA Event Extraction (GE) corpora of the BioNLP-ST 2011, and this corpora was used in the
evaluation of iSimp as well.

<!--
## Software

iSimp software can be downloaded via the [link](https://research.bioinformatics.udel.edu/isimp/corpus/isimp_v2.tar.gz).
This will download a large (213 MB) compressed file containing
(1) the iSimp code jar, (2) the iSimp rules, and (3) the libraries required to run iSimp. Unzip this file, open
the folder, and you’re ready to use it.
-->

## Sentence simplification corpus

This corpus consists of [Medline](http://www.ncbi.nlm.nih.gov/pubmed) abstracts concerning proteins and genes. We randomly selected 130 Medline abstracts (a total of 1,199 sentences), having the words "protein" and "gene" in the title (see table below). We asked five judges to mark the six constructs. To provide a high quality annotated corpus, each sentence was annotated by two judges independently and annotation conflicts (57 sentences in total) were solved by a third party opinion.

**iSimp corpus for sentence simplification annotation**
| Types                  | % of sentences | % of abstracts |
|:-----------------------|---------------:|---------------:|
| Coordinations          |           0.43 |           0.75 |
| Relative clauses       |           0.19 |           0.65 |
| Appositions            |           0.04 |           0.36 |
| Parenthesized elements |           0.16 |           0.63 |
| Introductory phrases   |           0.12 |           0.53 |

The corpus contains XML files of passages, sentences, and annotations of simplification constructions. Each XML file is in BioC format, therefore, given with its Key files. For convenience, all BioC files use same [DTD](https://github.com/yfpeng/isimp/releases/download/v0.2/BioC.dtd), provided by [BioC](http://www.ncbi.nlm.nih.gov/CBBresearch/Dogan/BioC/), to verify the structure of XML files.

* XML files, following DTD definition, contain text and annotations.
* Key files, mainly written by Yifan Peng, provide additional information that describe the meaning of tags in the XML files.

### Download

The download is a [zipped file](https://github.com/yfpeng/isimp/releases/download/v0.2/bioc-isimp-simplification_v2.tar.gz). If you upack the zip file, you should have a BioC XML file and a Key file. The BioC file contains raw text that appears in PubMed abstracts, which is downloaded directly from Medline, and simplfication constructs. For each construct, "annotation" marked its components, and "relation" linked components to show the simplification constructs as a whole. Details of tag meanings can be found in the Key file.

### BioNLP-ST corpora

We have converted the training and development corpora of the BioNLP-ST [2011](https://sites.google.com/site/bionlpst/) and [2013](https://sites.google.com/site/bionlpst2013/) GE tasks into BioC format. The converted corpora, as well as the conversion program, are available from the links below. The test corpora are not provided because the event annotations in those data are not released.

In the converted data below, text files (in .txt) in the BioNLP corpora are split by 'newlines' and stored into BioCPassages. Entities (in .a1) and event triggers (in .a2) are stored into separate passages based on their positions in the text files. Target annotations (in .a2), including event, relation, event modification, and equivalence, are annotated at the document level.

2013 GE task: [Training set](https://github.com/yfpeng/isimp/releases/download/v0.2/BioNLP-ST-2013_GE_train_data_rev3.xml.tar.gz), [Development set](https://github.com/yfpeng/isimp/releases/download/v0.2/BioNLP-ST-2013_GE_devel_data_rev3.xml.tar.gz)

2011 GE task: [Training set](https://github.com/yfpeng/isimp/releases/download/v0.2/BioNLP-ST_2011_genia_train_data_rev1.xml.tar.gz), [Development set](https://github.com/yfpeng/isimp/releases/download/v0.2/BioNLP-ST_2011_genia_devel_data_rev1.xml.tar.gz)

## Citing iSimp

* Peng Y, Tudor C, Torii M, Wu CH, Vijay-Shanker K. [iSimp: A Sentence Simplification System for Biomedical Text](https://doi.org/10.1109/BIBM.2012.6392671). In Proceedings of the 2012 IEEE International Conference on Bioinformatics and Biomedicine. 2012:211-216.

## Acknowledgment

Research reported in this website was supported by the National Library of Medicine of the National Institutes of
Health under award number G08LM010720. The content is solely the responsibility of the authors and does not
necessarily represent the official views of the National Institutes of Health.

This material is also based upon work supported by the National Science Foundation under Grant No. DBI-1062520.
Any opinions, findings, and conclusions or recommendations expressed in this material are those of the author(s)
and do not necessarily reflect the views of the National Science Foundation.

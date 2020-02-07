![iSimp](https://research.bioinformatics.udel.edu/isimp/images/isimplogo3.png)

-----------------------

Sentence simplification is a technique designed to detect the various types of clauses and constructs used in a
complex sentence, in an effort to produce two or more simple sentences while maintaining both coherence and the
communicated message. By reducing the syntactic complexity of a sentence, the goal of sentence simplification is
to ease the development of natural language processing and text mining tools. For this purpose, we developed **iSimp**.
You can download the software package [here](https://research.bioinformatics.udel.edu/isimp/software.html).

To illustrate the usefulness of sentence simplification, consider the following complex sentence from the biomedical
literature:

> Active Raf-1 phosphorylates and activates the mitogen-activated protein (MAP) kinase/extracellular signal-regulated
> kinase kinase 1 (MEK1), which in turn phosphorylates and activates the MAP kinases/extracellular signal regulated
> kinases, ERK1 and ERK2. (PMID-8557975)

The major syntactic constructs that we consider when simplifying a sentence are: coordinations, relative clauses,
appositions, parenthesized elements, and introductory phrases. Figure 1 shows constructs which can be seen in the
example. Figure 2 gives more details of components in each construct.

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

Downdload [lib] and put them in the `lib' folder.

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

## Software

iSimp software can be downloaded via the [link](https://research.bioinformatics.udel.edu/isimp/corpus/isimp_v2.tar.gz).
This will download a large (213 MB) compressed file containing
(1) the iSimp code jar, (2) the iSimp rules, and (3) the libraries required to run iSimp. Unzip this file, open
the folder, and you’re ready to use it.

## Citing iSimp

The main technical ideas behind how iSimp and BioC work appear in these papers. Feel free to cite one or more of the
following papers depending on what you are using.

* Peng,Y., Tudor,C., Torii,M., Wu,C.H., Vijay-Shanker,K. (2014) [iSimp in BioC standard format: Enhancing the interoperability of a sentence simplification system](http://database.oxfordjournals.org/content/2014/bau038). Database.
* Peng,Y., Tudor,C., Torii,M., Wu,C.H., Vijay-Shanker,K. (2013) [Enhancing the interoperability of iSimp by using the BioC format](http://www.biocreative.org/media/store/files/2013/ProceedingsBioCreativeIV_vol1_.pdf). In Proceedings of the Fourth BioCreative Challenge Evaluation Workshop. 5-9.
* Comeau,D.C., Doğan,R.I., Ciccarese,P., Cohen,K.B., Krallinger,M., Leitner,F., Lu,Z., Peng,Y., Rinaldi,F., Torii,M., Valencia,V., Verspoor,K., Wiegers,T.C., Wu,C.H., Wilbur,W.J. (2013) [BioC: A minimalist approach to interoperability for biomedical text processing](http://database.oxfordjournals.org/content/2013/bat064.abstract). Database: The Journal of Biological Databases and Curation.
* Peng,Y., Tudor,C., Torii,M., Wu,C.H., Vijay-Shanker,K. (2012) [iSimp: A Sentence Simplification System for Biomedical Text](http://ieeexplore.ieee.org/xpl/articleDetails.jsp?arnumber=6392671). In Proceedings of the 2012 IEEE International Conference on Bioinformatics and Biomedicine. 211-216.

## Acknowledgment

Research reported in this website was supported by the National Library of Medicine of the National Institutes of
Health under award number G08LM010720. The content is solely the responsibility of the authors and does not
necessarily represent the official views of the National Institutes of Health.

This material is also based upon work supported by the National Science Foundation under Grant No. DBI-1062520.
Any opinions, findings, and conclusions or recommendations expressed in this material are those of the author(s)
and do not necessarily reflect the views of the National Science Foundation.

This work was supported by the Intramural Research Programs of the National Institutes of Health, National Library of
Medicine and Clinical Center.

## Release history

Version 1.0.3 	2017-05-21 	Update the software package
Version 1.0.2 	2014-01-02 	Update the corpus
Version 1.0.1 	2013-10-05 	Minor bug fixes
Version 1.0 	2013-07-15 	Initial release

# Disclaimer

This tool shows the results of research conducted in the Computational Biology Branch, NCBI. The information produced
on this website is not intended for direct diagnostic use or medical decision-making without review and oversight
by a clinical professional. Individuals should not change their health behavior solely on the basis of information
produced on this website. NIH does not independently verify the validity or utility of the information produced
by this tool. If you have questions about the information produced on this website, please see a health care
professional. More information about NCBI's disclaimer policy is available.
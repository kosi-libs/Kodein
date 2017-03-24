#!/bin/sh

asciidoctor-pdf -a allow-uri-read -a icons=font@ -a source-highlighter=rouge -a doctype=book README3.adoc

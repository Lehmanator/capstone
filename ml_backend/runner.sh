#!/usr/bin/env bash

for i in 1 2 3 4 5 6 7 8 9 10
do
    word=$(http --body http://setgetgo.com/randomword/get.php)
    echo ${word}
    #python scraper.py -s $word + " logo" -n 200 -d non_logo
done
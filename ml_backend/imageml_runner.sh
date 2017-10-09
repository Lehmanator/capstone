#!/bin/bash
i=true
while [ $i ]
do
	err=$(python imageml.py | grep "IOError")
	echo $err
	if [ ! -z $err]; then
		path=echo err | sed "s/^IOError: cannot identify image file \'//"
		path=echo path | sed "s/\'^//"
		rm -f $path
	else
		$i=false
	fi
done


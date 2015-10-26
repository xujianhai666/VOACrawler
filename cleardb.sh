#!/bin/zsh

# 去服务器上试试吧
# 清空文件夹下的文件
mp3dir=`/Users/snow_young/tech/VoaHelper/mp3dir/*`
mp3files=`ls /Users/snow_young/tech/VoaHelper/mp3dir`
# 容易陷到目录中去
cd $mp3dir
rm -rf "/Users/snow_young/tech/VoaHelper/mp3dir/0335_heinlein_addis_ababa_lcr___ethiopia_aids_conference___2_acts_2m17s.wav-st.mp3"
# deletedcount=0
# for file in $mp3files;do
# 	# if [  $deletedcount -eq 0 ];then
# 	# if [  -d $mp3dir  ];then
# 	rm  -rf $file 
# 	let deletedcount=$deletedcount+1
# 	echo $file
# 	# fi
# 	# rm $file
# 	# 不能有空格
# 	# deletedcount=`expr $deletedcount + 1` mac下没有expr
# done
# echo "总共删除mp3文件总数 ： " $deletedcount
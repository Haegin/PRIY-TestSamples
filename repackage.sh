#!/bin/zsh

# use log files
[[ -e errors.log ]] && rm errors.log
[[ -e messags.log ]] && rm messags.log
touch errors.log
touch messages.log

# cd in to each project dir
for dir in *; do
    if [[ ( -d "${dir}" ) && ( -e "${dir}/pom.xml" ) ]]; then
        cd "${dir}"
        echo "Building ${dir}"
        mvn package >> ../messages.log 2>> ../errors.log
        echo "Done\n"
        cd ../
    fi
done

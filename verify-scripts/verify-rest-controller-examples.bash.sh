#!/usr/bin/env bash
##############################################################################################################################
# The bash script for verifying the results of compiler output for rest controller examples                              #
# Example:                                                                                                                   #
# bash verify-rest-controller-examples.bash.sh clean compile                                                           #
# ./verify-rest-controller-examples.bash.sh clean verify                                                               #
#                                                                                                                            #
# @author nedis                                                                                                              #
##############################################################################################################################
# Exit when any command fails:
set -eu -o pipefail

# Find the data r2dbc postgres examples:
EXAMPLE_FOLDERS=$(find ../examples -maxdepth 1 -name 'rest-controller-*' | sort)

# Iterate over sub folders:
for dir in $EXAMPLE_FOLDERS
do
echo "Verifying '$dir' example"
cd $dir
# Verify the results of compiler output:
mvn "$@"
cd ../../verify-scripts
printf "\n\n\n"
done
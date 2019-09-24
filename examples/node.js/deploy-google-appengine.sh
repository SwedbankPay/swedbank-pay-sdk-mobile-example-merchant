#! /bin/bash

# Stop script execution on any errors
set -e

bold=$(tput bold)
normal=$(tput sgr0)

if [ -z "$GCP_PROJECT_ID" ]; then
    echo ""
    echo "GCP_PROJECT_ID is not set!"
    exit
fi

project="$GCP_PROJECT_ID"

echo "Deploying app to Google AppEngine"
echo "Using GCP project ID ${bold}$project${normal}.."

gcloud app deploy --project=$project




import requests
import json

# Define Constants
API_INVOKE_URL="<INSERT_ASTAR_API_ENDPOINT_URL_HERE>"

# define variables
url=API_INVOKE_URL


def cloud_api_calcAStar(headers, payload):
    # send POST request to API Endpoint url
    return requests.request("POST", url, headers=headers, data=payload.encode('utf-8'))

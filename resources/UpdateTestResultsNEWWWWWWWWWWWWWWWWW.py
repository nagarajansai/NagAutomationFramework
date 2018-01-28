# Script to update the Executions based on Automation results

# Import from libraries 'urllib2', 'urllib', 'json', 'base64'
import pip
import os
import ConfigParser

def install_and_import(package):
    import importlib
    try:
        importlib.import_module(package)
    except ImportError:
        pip.main(['install', package])

from urllib2 import Request, urlopen
from urllib import urlencode
from json import load, dumps
from base64 import b64encode
install_and_import('lxml')
from lxml import etree
from xml.etree import ElementTree
import json
from pprint import pprint
from os import listdir
from os.path import isfile, join

configProperties = os.path.join(os.path.dirname(os.path.realpath(__file__)), 'gallopReporter.properties')
config = ConfigParser.RawConfigParser()
config.read(configProperties)

# Variables used:
username = config.get('Properties','jira.username')
password = config.get('Properties','jira.password')
testCycleName = config.get('Properties','jira.testCycleName')
fixVersion = config.get('Properties','pim.fixversion')
project = config.get('Properties','pim.project')
ticketsWithExecutionStatus = {}

# 'baseURL' holds basic data for JIRA server that will be used in API URL calls
baseURL = 'https://jira.mumms.com'
getExecutionsURL = baseURL + '/rest/zapi/latest/zql/executeSearch'
executeURL = baseURL + '/rest/zapi/latest/execution'

# 'headers' holds information to be sent with the JSON data set
# Initialized with Auth and Content-Type data
# Authorization header uses base64 encoded username and password string
headers = {"Authorization": "Basic " + b64encode(username + ":" + password), "Content-Type": "application/json"}

# ///// Save Automated Output as List /////
# Parse automation result file from automation tool
# Set execution status based on keyword string
print "Finding Automation Results File..."

summaryResultsPath = os.path.join(os.path.split(os.path.dirname(os.path.realpath(__file__)))[0], 'results')

for path, subdirs, files in os.walk(summaryResultsPath):
    for filename in files:
        if filename=='SummaryResults.html':
            f=open(path+'/'+filename)
            fileString = f.read()
            tree = etree.HTML(fileString)
            i=1
            for tr in tree.xpath('//tr[@class="content2"]'): 
                #print tree.xpath('(//tr[@class="content2"]//td/text())['+str(i*2)+']')[0]
                key = tree.xpath('(//tr[@class="content2"]//td/text())['+str(i*2)+']')[0]
                i=i+1
                value = 1 if tree.xpath('(//tr[@class="content2"]//td/text())['+str(i*2)+']')[0] == 'PASS' else 2
                i=i+1
                for x,y in [ (tickets.split("browse/")) for tickets in key.split(",") ]:
                    ticketsWithExecutionStatus[y] = value
                
print "Automation Results Fetched"


executionValues = {
    "zqlQuery": 'project = "'+ project +'" AND fixVersion = "'+fixVersion+'" AND cycleName in ("'+testCycleName+'")'
}

print "Finding the total count of executions ... "+str(executionValues)

# fetch first 20 records to find the max records count
getExecutionsURL = getExecutionsURL + "?" + urlencode(executionValues)
request =  Request(getExecutionsURL, None, headers=headers)
js_res = urlopen(request)
test = json.loads(js_res.read())
totalCount =  test['totalCount']
print "Total count of executions "+str(totalCount)

print "Fetching all the executions ... "
# Using the total count to fetch all the executions
getExecutionsURL = getExecutionsURL + "&offset=0&maxRecords="+str(totalCount)
request =  Request(getExecutionsURL, None, headers=headers)
js_res = urlopen(request)
fetchedResponse = load(js_res)
fetchedExecutions = fetchedResponse['executions']

for execution in fetchedExecutions:
    issueKey = execution['issueKey']
    if issueKey in ticketsWithExecutionStatus:
        status = ticketsWithExecutionStatus[issueKey]
        executionId = execution['id']
        data = dumps ({
                    "status": status
                })
        url = executeURL + '/' + str(executionId) + '/execute'
        request = Request(url, data=data, headers=headers)
        request.get_method = lambda:"PUT"
        response = urlopen(request)
        print response.read()
print "Successfully updated all the executions ... "
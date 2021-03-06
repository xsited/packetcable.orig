#!/usr/bin/env python

__author__ = 'xsited'
import os
import httplib
import json
import yaml
import base64
import string
from urlparse import urlparse
from pprint import pprint
from os.path import basename

toggle = 1

# consider refectoring with request http://docs.python-requests.org/en/latest/index.html

class Error:
    # indicates an HTTP error
    def __init__(self, url, errcode, errmsg, headers):
        self.url = url
        self.errcode = errcode
        self.errmsg = errmsg
        self.headers = headers
    def __repr__(self):
        return (
            "<Error for %s: %s %s>" %
            (self.url, self.errcode, self.errmsg)
            )


class RestfulAPI(object):
    def __init__(self, server):
        self.server = server
        self.path = '/wm/staticflowentrypusher/json'
        self.auth = ''
        self.port = 8080

    def get_server(self):
        return self.server

    def set_server(self, server):
        self.server = server


    def set_path(self, path):
	#print path
        self.path = path

#    def set_path(self, path, port):
#        self.path = path
#        self.port = port

    def set_port(self, port):
	#print port
        self.port = port

    def use_creds(self):
    	u = self.auth is not None and len(self.auth) > 0
#    	p = self.password is not None and len(self.password) > 0
        return u

    def credentials(self, username, password):
        self.auth = base64.encodestring('%s:%s' % (username, password)).replace('\n', '')

    def get(self, data=''):
        ret = self.rest_call({}, 'GET')
        #return json.loads(ret[2])
        return ret

    def set(self, data):
        #ret = self.rest_call(data, 'PUT')
        ret = self.rest_call2(data, 'PUT')
	print ret[0], ret[1]
        # return ret[0] == 200
        return ret

    def post(self, data):
        ret = self.rest_call(data, 'POST')
        #ret = self.rest_call2(data, 'POST')
	print ret[0], ret[1]
	return ret

    def put(self, data):
        ret = self.rest_call(data, 'PUT')
        return ret
        #return ret[0] == 200


    def remove(self, objtype, data):
        ret = self.rest_call(data, 'DELETE')
        #return ret[0] == 200
        return ret

    def show(self, data):
	print ""
	print json.dumps(data, indent=4, sort_keys=True)
#       print 'DATA:', repr(data)
#
#	print ""
#       data_string = json.dumps(data)
#       print 'JSON:', data_string
#
#	print ""
#       data_string = json.dumps(data)
#       print 'ENCODED:', data_string
#
#	print ""
#       decoded = json.loads(data_string)
#       print 'DECODED:', decoded


    def rest_call2(self, data, action, content_type='json'):

        #conn = httplib.HTTPConnection(self.server, self.port)
        conn = httplib.HTTP(self.server, self.port)
        conn.putrequest(action, self.path)
	conn.putheader("Host", self.server+':%s'%self.port)
 	conn.putheader("User-Agent", "Python HTTP Auth")
        conn.putheader('Content-type', 'application/%s' % content_type)
        body = json.dumps(data)
	#conn.putheader("Content-length", "%d" % len(data))
	conn.putheader("Content-length", "%d" % len(body))
        if self.use_creds():
        #    print "using creds"
            conn.putheader("Authorization", "Basic %s" % self.auth)
        conn.endheaders()
	
        conn.send(body)
	errcode, errmsg, headers = conn.getreply()
	ret = (errcode, errmsg, headers)

        #if errcode != 201:
        #   raise Error(self.path, errcode, errmsg, headers)

        # get response
        #response = conn.getresponse()
	#headers = response.read()
        #ret = (response.status, response.reason, headers)
        #if response.status != 200:
        #    raise Error(self.path, response.status, response.reason, headers)
	return ret


    def rest_call(self, data, action, content_type='json'):
	# this? 
        putheaders = {'content-type': 'application/json'}
        getheaders = {'Accept': 'application/json'}
        body = json.dumps(data)
	if self.use_creds():
	#    print "using creds"
            headers = {
                'Content-type': 'application/%s' % content_type,
                'Accept': 'application/%s' % content_type,
		'Content-length': "%d" % len(body),
	        'Authorization': "Basic %s" % self.auth,
            }
        else:
            headers = {
                'Content-type': 'application/%s' % content_type,
                'Accept': 'application/%s' % content_type,
		'Content-length': "%d" % len(body),
            }
		
	print self.server+':',self.port, self.path
        conn = httplib.HTTPConnection(self.server, self.port)
        conn.request(action, self.path, body, headers)
        response = conn.getresponse()
	data = response.read()
        ret = (response.status, response.reason, data)
        #print "status %d %s" % (response.status,response.reason)
        conn.close()
        return ret


class Menu(object):
    def __init__(self):
        pass

    def print_menu(self):
        print (30 * '-')
        print ("   CABLEFLOW          ")
        print (30 * '-')
        print ("1.  Add CMTS 1        ")
        print ("2.  Add CMTS 2        ")
        print ("3.  Add Flow 1 CMTS 1 ")
        print ("4.  Add Flow 2 CMTS 2 ")
        print ("5.  Remove Flow 1 CMTS 1")
        print ("6.  Remove Flow 2 CMTS 2")
        print ("7.  Remove All Flows  ")
        print ("8.  List Flow Stats   ")
        print ("9.  List Topology     ")
        print ("10. List Flows        ")
        print ("11. Remove CMTS 1     ")
        print ("12. Remove CMTS 2     ")
        print ("q. Quit               ")
#        print (30 * '-')


    def no_such_action(self):
        print "Invalid option!"

    def run(self):
	self.print_menu()
        actions = {
	"1": tests.flow_add_1, 
	"2": tests.flow_add_2, 
	"3": tests.flow_add_several, 
	"4": tests.flow_remove_1,
	"5": tests.flow_remove_2,
	"6": tests.flow_remove_all,
	"8": tests.flow_list_stats,
	"9": tests.topology_list,
	"10":tests.flow_list,
	"q": tests.exit_app,
        }

        while True:
            self.print_menu()
            selection = raw_input("Enter selection: ")
            if "quit" == selection:
                return
            toDo = actions.get(selection, self.no_such_action)
            toDo()




class ODLCableflowRestconf(object):
    def __init__(self):
	ws.set_port(8181)	

    def topology(self):
        ws.set_path('/restconf/operational/opendaylight-inventory:nodes')
        content = ws.get()
        j=json.loads(content[2])
        ws.show(j)

    def cableflow_list(self):
        ws.set_path('/config/opendaylight-inventory:nodes/node/%d/flow-node-inventory:table/0/flow')
        content = ws.get()
        j=json.loads(content[2])
        ws.show(j)
        #ws.show(content[2])
	return(j)

    def cableflow_update(self, flow):
        ws.set_path('/restconf/config/opendaylight-inventory:nodes/node/openflow:%d/table/0/flow/%d"' % flow['node']['id'],  flow['id'] )
        content = ws.post(flow)
        j=json.loads(content[2])

    def cableflow_list(self):
        ws.set_path('/restconf/config/opendaylight-inventory:nodes/node/openflow:%d/table/0/flow/%d')
        content = ws.get()
        j=json.loads(content[2])
        ws.show(j)
        #ws.show(content[2])

    def cableflow_add(self, flow):
# PUT http://localhost:8181/restconf/config/opendaylight-inventory:nodes/node/openflow:%d/table/0/flow/%d"
        ws.set_path('/restconf/config/opendaylight-inventory:nodes/node/openflow:%d/table/0/flow/%d"' % flow['node']['id'],  flow['id'] )
        ws.show(flow)
        content = ws.put(flow)
        #print content
	flowadd_response_codes = {
	201:"Flow Config processed successfully",
	400:"Failed to create Static Flow entry due to invalid flow configuration",
	401:"User not authorized to perform this operation",
	404:"The Container Name or nodeId is not found",
	406:"Cannot operate on Default Container when other Containers are active",
	409:"Failed to create Static Flow entry due to Conflicting Name or configuration",
	500:"Failed to create Static Flow entry. Failure Reason included in HTTP Error response",
	503:"One or more of Controller services are unavailable",
	} 
	msg=flowadd_response_codes.get(content[0])
	print content[0], content[1], msg

    def cableflow_remove(self, flow):
        ws.set_path('/restconf/config/opendaylight-inventory:nodes/node/openflow:%d/table/0/flow/%d"' % flow['node']['id'],  flow['id'] )
        content = ws.remove("", flow)

	flowdelete_reponse_codes = {
	204:"Flow Config deleted successfully",
	401:"User not authorized to perform this operation",
	404:"The Container Name or Node-id or Flow Name passed is not found",
	406:"Failed to delete Flow config due to invalid operation. Failure details included in HTTP Error response",
	500:"Failed to delete Flow config. Failure Reason included in HTTP Error response",
	503:"One or more of Controller service is unavailable",
	}
	msg=flowdelete_reponse_codes.get(content[0])
	print content[0], content[1], msg

    def cableflow_remove_all(self):
	allFlowConfigs = self.cableflow_list()
        flowConfigs = allFlowConfigs['flowConfig']
        for fl in flowConfigs:
	    print "Removing ", fl['name']
    	    self.cableflow_remove(fl)
		


    def statistics_flows(self):
        ws.set_path('/controller/nb/v2/statistics/default/flow')
        content = ws.get()
        allFlowStats = json.loads(content[2])

        flowStats = allFlowStats['flowStatistics']
	# These JSON dumps were handy when trying to parse the responses 
        #print json.dumps(flowStats[0]['flowStat'][1], indent = 2)
	#print json.dumps(flowStats[4], indent = 2)
        for fs in flowStats:
            print "\nSwitch ID : " + fs['node']['id']
	    print '{0:8} {1:8} {2:5} {3:15}'.format('Count', 'Action', 'Port', 'DestIP')
	    if not 'flowStatistic' in fs.values(): 
		print '              none'
		continue
	    for aFlow in fs['flowStatistic']:
		#print "*", aFlow, "*", " ", len(aFlow), " ", not aFlow
	        count = aFlow['packetCount']
	        actions = aFlow['flow']['actions'] 
	        actionType = ''
	        actionPort = ''
	        #print actions
	        if(type(actions) == type(list())):
		    actionType = actions[1]['type']
		    actionPort = actions[1]['port']['id']
		else:
	    	    actionType = actions['type']
		    actionPort = actions['port']['id']
		dst = aFlow['flow']['match']['matchField'][0]['value']
		print '{0:8} {1:8} {2:5} {3:15}'.format(count, actionType, actionPort, dst)


    def cableflow_remove_all(self):
	allFlowConfigs = self.cableflow_list()
        flowConfigs = allFlowConfigs['flowConfig']
        for fl in flowConfigs:
	    print "Removing ", fl['name']
    	    self.cableflow_remove(fl)
		


class CableflowTests(object):
    def __init__(self):
	self.flows = {}
    def cmts_add_1():
        print "Add cmts 1     "
        odl.cmts_add(cmts1)

    def cmts_add_2():
        print "Add cmts 2     "
        odl.cmts_add(cmts2)

    def cmts_remove_1():
        print "Add cmts 1     "
        odl.cmts_remove(cmts1)

    def cmts_remove_2():
        print "Add cmts 2     "
        odl.cmts_remove(cmts2)

    def flow_add_1():
        print "Add Flow 1     "
        odl.cableflow_add(flow1)


    def flow_add_2():
        print "Add Flow 2     "
        odl.cableflow_add(flow2)

    def flow_add_several():
        print "Add Flow Several     "
        odl.cableflow_add(flow1)
        odl.cableflow_add(flow2)
        odl.cableflow_add(flow3)
        odl.cableflow_add(flow4)
        odl.cableflow_add(flow5)


    def flow_remove_1():
        print "Remove Flow 1  "
        odl.cableflow_remove(flow1)

    def flow_remove_2():
        print "Remove Flow 2  "
        odl.cableflow_remove(flow2)

    def flow_remove_all():
        print "Remove All Flows "
        odl.cableflow_remove_all()

    def flow_list_stats():
        print "List Flow Stats"
        odl.statistics_flows()

    def topology_list():
        print "List Topology  "
        odl.topology()

    def flow_list():
        print "List Flows  "
        odl.cableflow_list()


    def flows_read(self, content_type='json'):
        #print "content_type = %s" % content_type
	for path, dirs, files in os.walk('.'):
            for filename in files:
                if filename.endswith(".%s" % content_type):
                    base_filename = basename(filename)
                    print base_filename
                    #print filename
	            with open(filename) as fp:
                        data = fp.read()
                        print(data)
                        # jdata = yaml.load ( data  )
                        # print(jdata)
                        self.flows[filename]=data #equivalent to: self.varname= 'something'
			if content_type == "xml":
                            pprint (self.flows[filename], width=4)
			else:
                            # pprint (self.flows[filename], width=4)
                            json.dumps(json.loads(data), indent=4)

    def exit_app():
        print "Quit           "
        exit(0)

ws = RestfulAPI('127.0.0.1')

if __name__ == "__main__":
    ws.credentials('admin', 'admin')
    odl = ODLCableflowRestconf()
    tests = CableflowTests()
    tests.flows_read()

    exit(0)
    menu=Menu()
    menu.run()
    exit(0)




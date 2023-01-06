#
# The server creates one single object adapter with the name
# "Hello". The following line sets the endpoints for this
# adapter.
#
Adapter.Endpoints=tcp -h rental -p 5022 -z -t 5022 : udp -h rental -p 5022 -z

Adapter.ThreadPool.Size=1

#
# Warn about connection exceptions
#
Ice.Warn.Connections=1

#
# Network Tracing
#
# 0 = no network tracing
# 1 = trace connection establishment and closure
# 2 = like 1, but more detailed
# 3 = like 2, but also trace data transfer
#
Ice.Trace.Network=1

#
# Protocol Tracing
#
# 0 = no protocol tracing
# 1 = trace protocol messages
#
#Ice.Trace.Protocol=1

#
# Security Tracing
#
# 0 = no security tracing
# 1 = trace messages
#
#IceSSL.Trace.Security=1

#
# SSL Configuration
#
#Ice.Plugin.IceSSL=IceSSL.PluginFactory
#IceSSL.DefaultDir=certs
#IceSSL.Keystore=server.jks
#IceSSL.Password=password
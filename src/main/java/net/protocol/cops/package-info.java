/**
 *  <pre>
 1. Introduction

 This document describes a simple query and response protocol that can
 be used to exchange policy information between a policy server
 (Policy Decision Point or PDP) and its clients (Policy Enforcement
 Points or PEPs).  One example of a policy client is an RSVP router
 that must exercise policy-based admission control over RSVP usage
 [RSVP].  We assume that at least one policy server exists in each
 controlled administrative domain. The basic model of interaction
 between a policy server and its clients is compatible with the
 framework document for policy based admission control [WRK].

 A chief objective of this policy control protocol is to begin with a
 simple but extensible design. The main characteristics of the COPS
 protocol include:

 1. The protocol employs a client/server model where the PEP sends
 requests, updates, and deletes to the remote PDP and the PDP
 returns decisions back to the PEP.

 2. The protocol uses TCP as its transport protocol for reliable
 exchange of messages between policy clients and a server.
 Therefore, no additional mechanisms are necessary for reliable
 communication between a server and its clients.

 3. The protocol is extensible in that it is designed to leverage
 off self-identifying objects and can support diverse client
 specific information without requiring modifications to the
 COPS protocol itself. The protocol was created for the general
 administration, configuration, and enforcement of policies.

 4. COPS provides message level security for authentication, replay
 protection, and message integrity. COPS can also reuse existing
 protocols for security such as IPSEC [IPSEC] or TLS to
 authenticate and secure the channel between the PEP and the
 PDP.

 5. The protocol is stateful in two main aspects:  (1)
 Request/Decision state is shared between client and server and
 (2) State from various events (Request/Decision pairs) may be
 inter-associated. By (1) we mean that requests from the client
 PEP are installed or remembered by the remote PDP until they
 are explicitly deleted by the PEP. At the same time, Decisions
 from the remote PDP can be generated asynchronously at any time


 for a currently installed request state. By (2) we mean that
 the server may respond to new queries differently because of
 previously installed Request/Decision state(s) that are
 related.

 6. Additionally, the protocol is stateful in that it allows the
 server to push configuration information to the client, and
 then allows the server to remove such state from the client
 when it is no longer applicable.

 1.1 Basic Model

 +----------------+
 |                |
 |  Network Node  |            Policy Server
 |                |
 |   +-----+      |   COPS        +-----+
 |   | PEP |<-----|-------------->| PDP |
 |   +-----+      |               +-----+
 |    ^           |
 |    |           |
 |    \-->+-----+ |
 |        | LPDP| |
 |        +-----+ |
 |                |
 +----------------+

 Figure 1: A COPS illustration.
 </pre>
 *
 * @see <a href="http://www.ietf.org/rfc/rfc2748.txt">The COPS (Common Open Policy Service) Protocol</a>
 * @author jinhongw@gmail.com
 */
package net.protocol.cops;


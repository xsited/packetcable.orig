#
# define compiler and compiler flag variables
#

JFLAGS = -g
JC = javac
JAR_PKG = ../ara.jar
TARBALL = ara.tar.gz

#
# Clear any default targets for building .class files from .java files; we 
# will provide our own target entry to do this in this makefile.
# make has a set of default targets for different suffixes (like .c.o) 
# Currently, clearing the default for .java.class is not necessary since 
# make does not have a definition for this target, but later versions of 
# make may, so it doesn't hurt to make sure that we clear any default 
# definitions for these
#

.SUFFIXES: .java .class


#
# Here is our target entry for creating .class files from .java files 
# This is a target entry that uses the suffix rule syntax:
#	DSTS:
#		rule
#  'TS' is the suffix of the target file, 'DS' is the suffix of the dependency 
#  file, and 'rule'  is the rule for building a target	
# '$*' is a built-in macro that gets the basename of the current target 
# Remember that there must be a < tab > before the command line ('rule') 
#

.java.class:
	$(JC) $(JFLAGS) $*.java


#
# SOURCES files
#
SOURCES = \
 	./auxClasses/CacheLine.java \
 	./auxClasses/Cache.java \
 	./auxClasses/PacketAssembler.java \
 	./auxClasses/Policy.java \
 	./auxClasses/PolicyRepository.java \
 	./auxClasses/StringParser.java \
 	./cops/PDP.java \
  	./cops/PDPThread.java \
 	./cops/PEP.java \
 	./cops/PEPThreadCacheUpdate.java \
 	./cops/PEPThreadReplyer.java \
 	./cops/PEPThreadReq.java \
 	./copsMsgs/CopsMessageCAT.java \
 	./copsMsgs/CopsMessageCC.java \
 	./copsMsgs/CopsMessageDEC.java \
 	./copsMsgs/CopsMessageDRQ.java \
 	./copsMsgs/CopsMessageKA.java \
 	./copsMsgs/CopsMessageOPN.java \
 	./copsMsgs/CopsMessageREQ.java \
 	./copsMsgs/CopsMessageRPT.java \
 	./copsMsgs/CopsMessageSSC.java \
 	./copsMsgs/CopsMessageSSQ.java \
 	./copsObjs/CopsAcctTimerObj.java \
 	./copsObjs/CopsClientSIObj.java \
 	./copsObjs/CopsCommonHeader.java \
 	./copsObjs/CopsContext.java \
 	./copsObjs/CopsDecisionObj.java \
 	./copsObjs/CopsErrorObj.java \
 	./copsObjs/CopsHandleObj.java \
 	./copsObjs/CopsInInterfaceObj.java \
 	./copsObjs/CopsKATimerObj.java \
 	./copsObjs/CopsLastPDPAddrObj.java \
 	./copsObjs/CopsLPDPDecisionObj.java \
 	./copsObjs/CopsObj.java \
 	./copsObjs/CopsOutInterfaceObj.java \
 	./copsObjs/CopsPDPRedirAddrObj.java \
 	./copsObjs/CopsPEPIDObj.java \
 	./copsObjs/CopsReasonObj.java \
 	./copsObjs/CopsReportTypeObj.java 


CLASSES = $(SOURCES:%.java=%.class)

#
# the default make target entry
#

default: classes jar

#
# This target entry uses Suffix Replacement within a macro: 
# $(name:string1=string2)
# 	In the words in the macro named 'name' replace 'string1' with 'string2'
# Below we are replacing the suffix .java of all words in the macro CLASSES 
# with the .class suffix
#

classes: $(SOURCES:.java=.class)

jar:
	jar cvf $(JAR_PKG) $(CLASSES)

tar:
	tar czpf $(TARBALL) $(SOURCES) Makefile


#
# RM is a predefined macro in make (RM = rm -f)
#

clean:
	$(RM) $(CLASSES)  $(JAR_PKG)


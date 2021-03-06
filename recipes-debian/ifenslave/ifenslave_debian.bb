#
# base recipe: http://cgit.openembedded.org/cgit.cgi/meta-openembedded/tree/\
#		meta-networking/recipes-support/ifenslave/ifenslave_1.1.0.bb?
# base branch: master
# base commit: acc086420ca087c8d044b60bd5d96e28426fd840
#

SUMMARY = "Configure network interfaces for parallel routing"
DESCRIPTION = " This is a tool to attach and detach slave network interfaces to \
		a bonding device. A bonding device will act like a normal 	\
		Ethernet network device to the kernel, but will send out the 	\
		packets via the slave devices using a simple round-robin 	\
		scheduler. This allows for simple load-balancing, identical 	\
		to "channel bonding" or "trunking" techniques used in switches"
HOMEPAGE = "http://www.linuxfoundation.org/collaborate/workgroups/networking/bonding"

PR = "r0"
inherit debian-package

LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://ifenslave;md5=5b971c6ad58cb00d10e12e5bf460037c"

#Install follow Debian jessies
do_install() {
	#Create new folders
	install -d ${D}${sbindir}
	install -m 755 ${S}/ifenslave ${D}${sbindir}/
	install -d ${D}${sysconfdir}
	install -d ${D}${sysconfdir}/network
	install -d ${D}${sysconfdir}/network/if-post-down.d
	install -d ${D}${sysconfdir}/network/if-pre-up.d
	install -d ${D}${sysconfdir}/network/if-up.d
	
	ln -s ifenslave ${D}${sbindir}/ifenslave-2.6
	install -m 0755 ${S}/debian/ifenslave.if-post-down 		\
			${D}${sysconfdir}/network/if-post-down.d/ifenslave

	install -m 0755 ${S}/debian/ifenslave.if-pre-up 		\
			${D}${sysconfdir}/network/if-pre-up.d/ifenslave

	install -m 0755 ${S}/debian/ifenslave.if-up 			\
			${D}${sysconfdir}/network/if-up.d/ifenslave
}

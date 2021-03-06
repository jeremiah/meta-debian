# base recipe: meta/recipes-support/libbsd/libbsd_0.7.0.bb
# base branch: master

# Copyright (C) 2013 Khem Raj <raj.khem@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Library of utility functions from BSD systems"
DESCRIPTION = "This library provides useful functions commonly found on BSD systems, \
               and lacking on others like GNU systems, thus making it easier to port \
               projects with strong BSD origins, without needing to embed the same \
               code over and over again on each project."

HOMEPAGE = "http://libbsd.freedesktop.org/wiki/"

PR = "r0"

inherit debian-package

LICENSE = "BSD-4-Clause & MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=f1530ea92aeaa1c5e2547cfd43905d8c"
SECTION = "libs"
DEPENDS = ""

inherit autotools pkgconfig

do_install_append() {
	# move libbsd.so.0* file from ${libdir} to ${base_libdir} as debian jessie
	install -d ${D}${base_libdir}
	mv ${D}${libdir}/libbsd.so.0* ${D}${base_libdir}/
	
	# Relink library
	rel_lib_prefix=`echo ${libdir} | sed 's,\(^/\|\)[^/][^/]*,..,g'`	
	libname=`readlink ${D}${libdir}/libbsd.so | xargs basename`
	ln -sf ${rel_lib_prefix}${base_libdir}/${libname} ${D}${libdir}/libbsd.so
}

FILES_${PN}0 += "${base_libdir}/*"


# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-jordicrosmompart.git;protocol=ssh;branch=master \
           file://0001-Makefile-only-builds-scull-and-misc-modules.patch \
           file://misc-modules-start-stop.sh \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "82f0f495542f18ca50cb6621b1f45ee9cf74ebe3"

S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/scull"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"


inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "misc-modules-start-stop.sh"

FILES:${PN} += "${sysconfdir}/*"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {

	install -d ${D}${sysconfdir}/init.d
    install -d ${D}${base_libdir}/modules/5.15.68-yocto-standard/
	install -m 0755 ${WORKDIR}/misc-modules-start-stop.sh ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/misc-modules/hello.ko ${D}${base_libdir}/modules/5.15.68-yocto-standard/
    install -m 0755 ${S}/misc-modules/faulty.ko ${D}${base_libdir}/modules/5.15.68-yocto-standard/
}

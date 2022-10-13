# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "Unknown"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-jordicrosmompart.git;protocol=ssh;branch=master \
           file://0001-Only-build-scull-and-misc.patch \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "82f0f495542f18ca50cb6621b1f45ee9cf74ebe3"

S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/misc-modules"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "misc-modules-start-stop.sh"

do_compile () {
	oe_runmake
}

SCRIPT_INIT="file://misc-modules-start-stop.sh"

do_install () {

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${SCRIPT_INIT} ${D}${sysconfdir}/init.d
}
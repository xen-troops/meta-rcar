SUMMARY = "RPC HyperFlash Linux driver"
DESCRIPTION = "RPC HyperFlash Linux MTD platform driver."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = " \
    file://GPL-COPYING;md5=60422928ba677faaa13d6ab5f5baaa1e \
"
inherit module

RDEPENDS_${PN}_append = " kernel-modules"

COMPATIBLE_MACHINE = "(r8a7795|r8a7796)"

SRC_URI = " \
    file://rpc_hyperflash.tar.bz2 \
"
S = "${WORKDIR}/rpc_hyperflash"

LDFLAGS[unexport] = "1"
do_configure[noexec] = "1"

do_install() {
    # Create shared folder
    install -d ${D}/lib/modules/${KERNEL_VERSION}/extra/

    # Copy kernel module
    install -m 0644 ${S}/rpc_hyperflash.ko ${D}/lib/modules/${KERNEL_VERSION}/extra/
}

PACKAGES = "\
    ${PN} \
"

FILES_${PN} = " \
    /lib/modules/${KERNEL_VERSION}/extra \
"

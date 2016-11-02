SUMMARY = "R-Car RPC HyperFlash Linux platform device"
DESCRIPTION = "This kernel module creates R-Car RPC HyperFlash MTD platform device and partitions."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = " \
    file://GPL-COPYING;md5=60422928ba677faaa13d6ab5f5baaa1e \
"
inherit module

RDEPENDS_${PN}_append = " kernel-module-rpc-hyperflash"

COMPATIBLE_MACHINE = "(r8a7795|r8a7796)"

SRC_URI = " \
    file://rcar_rpc_hyperflash.tar.bz2 \
"
S = "${WORKDIR}/rcar_rpc_hyperflash"

LDFLAGS[unexport] = "1"
do_configure[noexec] = "1"

do_install() {
    # Create shared folder
    install -d ${D}/lib/modules/${KERNEL_VERSION}/extra/

    # Copy kernel module
    install -m 0644 ${S}/rcar_rpc_hyperflash.ko ${D}/lib/modules/${KERNEL_VERSION}/extra/
}

PACKAGES = "\
    ${PN} \
"

FILES_${PN} = " \
    /lib/modules/${KERNEL_VERSION}/extra \
"

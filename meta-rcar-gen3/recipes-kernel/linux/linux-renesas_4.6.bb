DESCRIPTION = "Linux kernel for the R-Car Generation 3 based board"

require include/avb-control.inc
require recipes-kernel/linux/linux-yocto.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/${MACHINE}:"
COMPATIBLE_MACHINE = "(salvator-x|h3ulcb|m3ulcb)"

RENESAS_BSP_URL = "git://git.kernel.org/pub/scm/linux/kernel/git/horms/renesas-bsp.git"
BRANCH = "v4.6/rcar-3.3.x"
SRCREV = "f100fac1e2a41c8f0d52f7b5607472a5e5e7c010"

SRC_URI = "${RENESAS_BSP_URL};protocol=git;nocheckout=1;branch=${BRANCH}"

LINUX_VERSION ?= "4.6"
PV = "${LINUX_VERSION}+git${SRCPV}"
PR = "r1"

SRC_URI_append = " \
    file://defconfig \
    file://touch.cfg \
    ${@base_conditional("USE_AVB", "1", " file://usb-video-class.cfg", "", d)} \
    file://0001-arm64-renesas-Add-H3ULCB-board.patch \
    file://0002-staging-boards-Add-H3ULCB-staging.patch \
    file://0003-spi-sh-msiof-fixes.patch \
    file://0004-spi-spidev-add-spi-gpio-into-spidev.patch \
    file://0005-spi-spi-gpio-fix-CPOL-mode.patch \
    file://0006-xhci-rcar-add-firmware-for-R-Car-H2-M2-USB-3.0-host-.patch \
    file://0007-usb-host-xhci-plat-add-support-for-the-R-Car-H3-xHCI.patch \
    file://0008-spi-spi-gpio-fix-set-CPOL-default-inverted.patch \
    file://0009-Revert-media-soc_camera-rcar_vin-Fix-VIDIOC_S_CROP-ioctl-mi.patch \
    file://0010-mmc-sh_mobile_sdhi-Add-R-CarGen3-SDHI-SEQUENCER-supp.patch \
    file://0011-arm64-renesas-Add-M3ULCB-board.patch \
    file://0012-staging-boards-Add-M3ULCB-staging.patch \
    file://0019-can-rcar_can-add-enable-and-standby-control-pins.patch \
    file://0020-can-rcar-canfd-Add-Renesas-R-Car-CAN-FD-driver.patch \
    file://0021-arm64-dts-r8a7795-Add-CAN-FD-support.patch \
    file://0022-can-rcar_canfd-use-explicit-clock_select-from-dts.patch \
    file://0023-can-rcar_canfd-add-enable-and-standby-control-pins.patch \
    file://0024-mtd-Add-RPC-HyperFlash-driver.patch \
    file://0025-IMR-driver-interim-patch.patch \
    file://0040-H3-Maxim-MAX9286-support-support-10635-10640-cameras.patch \
    file://0050-arm64-renesas-Salvator-X-View-H3-board-support.patch \
    file://0051-arm64-renesas-H3ULCB-HAD-support.patch \
    file://0052-arm64-renesas-H3ULCB-View-board-support.patch \
    file://0053-arm64-renesas-Salvator-X-View-M3-board-support.patch \
    file://0054-arm64-renesas-M3ULCB-View-board-support.patch \
    file://0055-arm64-dts-r8a7795-h3ulcb-had-set-console-from-rdrive.patch \
    file://0056-arm64-dts-r8a7795-h3ulcb-had-route-RAVB-to-rdrive.patch \
    ${@base_conditional("LVDSCAMERA_ONE", "1", " file://0057-arm64-dts-Gen3-view-boards-stream-from-1-cam.patch", "", d)} \
    ${@base_conditional("LVDSCAMERA_FIVE", "1", " file://0058-arm64-dts-Gen3-view-boards-stream-from-5-cam.patch", "", d)} \
    ${@base_conditional("RAVB_DEBUG", "1", " file://0059-net-ethernet-renesas-ravb-packets-dump.patch", "", d)} \
    file://0001-media-i2c-max9286_max9271_ov106xx-Fix-building-as-a-.patch \
    file://0002-phy-Enable-USB_PHY-for-PHY_RCAR_GEN3_USB2.patch \
    file://0003-ARM64-dts-r8a7795-salvator-x-Fix-a-typo-in-the-hdmi-.patch \
    file://0004-ARM64-dts-r8a7796-salvator-x-Fix-a-typo-in-the-hdmi-.patch \
    file://0005-ARM64-dts-r8a7795-salvator-x-Add-gpio-keys-device-no.patch \
    file://0006-ARM64-dts-r8a7795-salvator-x-view-Add-gpio-keys-devi.patch \
    file://0007-ARM64-dts-r8a7796-salvator-x-Add-gpio-keys-device-no.patch \
    file://0008-ARM64-dts-r8a7796-salvator-x-view-Add-gpio-keys-devi.patch \
    file://0009-ARM64-dts-r8a7795-h3ulcb-Add-more-buttons-to-gpio-ke.patch \
    file://0010-ARM64-dts-r8a7795-h3ulcb-view-Add-more-buttons-to-gp.patch \
    file://0011-ARM64-dts-r8a7795-h3ulcb-had-Add-more-buttons-to-gpi.patch \
    file://0012-ARM64-dts-r8a7796-m3ulcb-Add-more-buttons-to-gpio-ke.patch \
    file://0013-ARM64-dts-r8a7796-m3ulcb-view-Add-more-buttons-to-gp.patch \
    file://0014-ARM64-dts-renesas-Add-r8a77xx-panel.dtsi.patch \
    file://0015-ARM64-dts-r8a7795-salvator-x-Use-r8a77xx-panel.dtsi.patch \
    file://0016-ARM64-dts-r8a7795-salvator-x-view-Use-r8a77xx-panel..patch \
    file://0017-ARM64-dts-r8a7796-salvator-x-Use-r8a77xx-panel.dtsi.patch \
    file://0018-ARM64-dts-r8a7796-salvator-x-view-Use-r8a77xx-panel..patch \
    ${@base_conditional("LCD_PANEL_MODEL", "TX31D200VM0BAA", "file://0019-ARM64-dts-r8a7795-salvator-x-Set-KOE-TX31D200VM0BAA-.patch", "", d)} \
    ${@base_conditional("LCD_PANEL_MODEL", "AA121TD01", "file://0021-ARM64-dts-r8a7795-salvator-x-Set-Mitsubishi-AA121TD0.patch", "", d)} \
    ${@base_conditional("LCD_PANEL_MODEL", "RFWSVGAT-7x0AP-APL", "file://0023-ARM64-dts-r8a7795-salvator-x-Set-Okaya-RFWSVGAT-7x0A.patch", "", d)} \
"

SRC_URI_append_h3ulcb = " \
    file://h3ulcb.cfg \
"

SRC_URI_append_salvator-x = " \
    file://salvator-x.cfg \
"

SRC_URI_append_m3ulcb = " \
    file://m3ulcb.cfg \
"

KERNEL_DEVICETREE_append_h3ulcb = '${@ \
    " renesas/r8a7795-h3ulcb-had.dtb " if 'h3ulcb-had' in '${MACHINE_FEATURES}' else \
    " renesas/r8a7795-h3ulcb-view.dtb " if 'h3ulcb-view' in '${MACHINE_FEATURES}' else \
    ""}'

KERNEL_DEVICETREE_append_salvator-x = '${@ \
    " renesas/r8a7795-salvator-x-view.dtb " if 'salvator-x-view' in '${MACHINE_FEATURES}' and 'r8a7795' in '${SOC_FAMILY}' else \
    " renesas/r8a7796-salvator-x-view.dtb " if 'salvator-x-view' in '${MACHINE_FEATURES}' and 'r8a7796' in '${SOC_FAMILY}' else \
    ""}'

KERNEL_DEVICETREE_append_m3ulcb = '${@ \
    " renesas/r8a7796-m3ulcb-view.dtb " if 'm3ulcb-view' in '${MACHINE_FEATURES}' else \
    ""}'

require include/omx-options.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_rcar-gen3 = " \
    git://github.com/renesas-rcar/weston.git;protocol=git;branch=rcar-gen3/1.11.0/v4l2-renderer \
    file://weston.png \
    file://weston.desktop \
    file://0001-make-error-portable.patch \
    file://linux-dmabuf.xml \
"

GL_SRCREV = "2d825ed9eb0388d47e9fc14294f6f6d63d5c230c"
V4L2_SRCREV = "90ee5bdb3f10a7daedc30b726b767eed6f24ab70"

SRCREV_rcar-gen3 = '${@base_conditional("USE_MULTIMEDIA", "1", "${V4L2_SRCREV}", "${GL_SRCREV}", d)}'

SRC_URI_append_rcar-gen3 = " \
    file://0001-protocol-Add-pkgconfig-file-to-be-referred-from-clie.patch \
    file://0001-configure.ac-Fix-wayland-protocols-path.patch \
    file://weston.ini \
    ${@base_conditional("USE_MULTIMEDIA", "1", " file://weston_v4l2.ini", "", d)} \
"

S = "${WORKDIR}/git"

PACKAGECONFIG_append = " \
    ${@base_conditional('USE_MULTIMEDIA', '1', ' v4l2', '', d)} \
"

PACKAGECONFIG[v4l2] = " --enable-v4l2,,libmediactl-v4l2,kernel-module-vsp2driver"

do_install_append_rcar-gen3() {
    # install weston.ini as sample settings of v4l2-renderer
    if [ "X${USE_MULTIMEDIA}" = "X1" ]; then
        # install xml for client applications
        install -d ${D}${datadir}/wayland-protocols/
        install -m 644 ${WORKDIR}/linux-dmabuf.xml ${D}${datadir}/wayland-protocols/

        # install weston.ini as sample settings of v4l2-renderer
        install -d ${D}/${sysconfdir}/xdg/weston
        install -m 644 ${WORKDIR}/weston_v4l2.ini ${D}/${sysconfdir}/xdg/weston/weston.ini
    else
        # install xml for client applications
        install -d ${D}${datadir}/wayland-protocols/
        install -m 644 ${WORKDIR}/linux-dmabuf.xml ${D}/${datadir}/wayland-protocols/

        # install weston.ini as sample settings of gl-renderer

        install -d ${D}/${sysconfdir}/xdg/weston
        install -m 644 ${WORKDIR}/weston.ini ${D}/${sysconfdir}/xdg/weston/
    fi
}

FILES_${PN}_append_rcar-gen3 = " \
    ${sysconfdir}/xdg/weston/weston.ini \
"

package com.eighth.housekeeping.domain;

import com.eighth.housekeeping.domain.annotation.Column;
import com.eighth.housekeeping.domain.annotation.Table;

/**
 * Created by dam on 2014/7/11.
 */
@Table(name="t_apk_version",comment = "apk版本")
public class APKVersion {
    @Column(name="last_version_code",pk=true,length = 10,comment = "最新版本号")
    private String lastVersionCode;//最新版本号
    @Column(name="download_url",length = 32,comment = "下载链接")
    private String downloadUrl;//下载链接

    public String getLastVersionCode() {
        return lastVersionCode;
    }

    public void setLastVersionCode(String lastVersionCode) {
        this.lastVersionCode = lastVersionCode;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}

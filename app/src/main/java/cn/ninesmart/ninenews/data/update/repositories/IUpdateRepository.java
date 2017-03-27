package cn.ninesmart.ninenews.data.update.repositories;

import cn.ninesmart.ninenews.data.update.models.VersionInfo;
import rx.Observable;

/**
 * Author   : perqin
 * Date     : 17-3-28
 */

public interface IUpdateRepository {
    VersionInfo getCurrentVersion();

    Observable<VersionInfo> checkLatestVersion();

    boolean isUpdating();

    Observable<Void> startUpdating(VersionInfo info);

    Observable<Void> cancelUpdating();
}

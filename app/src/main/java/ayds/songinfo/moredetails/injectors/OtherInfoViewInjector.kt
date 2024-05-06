package ayds.songinfo.moredetails.injectors

import ayds.songinfo.moredetails.injectors
import ayds.songinfo.moredetails.presentation.OtherInfoView

object OtherInfoViewInjector {

    fun init(view: OtherInfoView) {
        RepositorylInjector.initRepository(view)
        OtherInfoPresenterInjector.onViewStarted(view)
    }
}
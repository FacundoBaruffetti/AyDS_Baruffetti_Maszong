package ayds.songinfo.home.controller

import ayds.songinfo.moredetails.injectors

object OtherInfoPresenterInjector {

    fun onViewStarted(view: OtherInfoView) {
        ayds.songinfo.moredetails.presentation.OtherInfoPresenterImpl(RepositoryInjector.getRepository())
    }
}
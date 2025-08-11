package play.pluv.playlist.exception

import play.pluv.base.BaseException

class PlayListException(playListExceptionType: PlayListExceptionType) :
    BaseException(playListExceptionType)

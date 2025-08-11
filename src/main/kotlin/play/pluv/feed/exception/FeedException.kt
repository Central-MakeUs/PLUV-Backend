package play.pluv.feed.exception

import play.pluv.base.BaseException

class FeedException(feedExceptionType: FeedExceptionType) : BaseException(feedExceptionType)

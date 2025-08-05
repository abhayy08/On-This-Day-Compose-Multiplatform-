package com.abhay.onthisday.presentation

import com.abhay.onthisday.domain.util.DataError
import com.abhay.onthisday.presentation.util.UiText
import onthisday.composeapp.generated.resources.Res
import onthisday.composeapp.generated.resources.error_disk_full
import onthisday.composeapp.generated.resources.error_no_internet
import onthisday.composeapp.generated.resources.error_request_timeout
import onthisday.composeapp.generated.resources.error_serialization
import onthisday.composeapp.generated.resources.error_too_many_requests
import onthisday.composeapp.generated.resources.error_unknown

fun DataError.toUiText(): UiText {
    val stringRes = when(this) {
        DataError.Local.DISK_FULL -> Res.string.error_disk_full
        DataError.Local.UNKNOWN -> Res.string.error_unknown
        DataError.Remote.REQUEST_TIMEOUT -> Res.string.error_request_timeout
        DataError.Remote.TOO_MANY_REQUESTS -> Res.string.error_too_many_requests
        DataError.Remote.NO_INTERNET -> Res.string.error_no_internet
        DataError.Remote.SERVER -> Res.string.error_unknown
        DataError.Remote.SERIALIZATION -> Res.string.error_serialization
        DataError.Remote.UNKNOWN -> Res.string.error_unknown
    }

    return UiText.StringResourceId(stringRes)
}
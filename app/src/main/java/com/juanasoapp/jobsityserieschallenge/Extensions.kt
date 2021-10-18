package com.juanasoapp.jobsityserieschallenge

import android.text.Html
import android.text.Spanned

fun setTextHTML(html:String): Spanned {
    var result:Spanned = if(android.os.Build.VERSION.SDK_INT>= android.os.Build.VERSION_CODES.N){
        Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY)
    }else{
        Html.fromHtml(html)
    }
    return result
}
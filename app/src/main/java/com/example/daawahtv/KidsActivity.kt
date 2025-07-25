package com.example.daawahtv

import android.os.Bundle
import androidx.fragment.app.FragmentActivity

/**
 * Activity بسيطة لاستضافة KidsFragment.
 */
class KidsActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kids) // تأكد من وجود هذا التخطيط

        if (savedInstanceState == null) {
            // استضافة KidsFragment داخل هذه الـ Activity
            supportFragmentManager.beginTransaction()
                .replace(R.id.kids_fragment_container, KidsFragment())
                .commitNow()
        }
    }
}

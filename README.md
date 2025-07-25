# 📺 Daawah TV – Android TV App

تطبيق Native Android TV لبث قناة دعوة وعرض البرامج عبر واجهة مبسطة وقابلة للتحكم بالريموت. تم تطويره باستخدام Kotlin في Android Studio، ويعتمد على **Streamit API** من موقع daawah.tv.

---

## 🔧 مميزات التطبيق

- تشغيل البث المباشر لقناة دعوة عبر ExoPlayer.
- عرض البرامج المصنّفة في سلايدر رئيسي.
- عرض تفاصيل البرنامج وقائمة الحلقات.
- دعم الريموت بشكل كامل.
- واجهة بسيطة وسريعة.
- إمكانية التشغيل التلقائي للحلقات المتتالية.
- صفحة مستقلة لبرامج الأطفال.
- سياسة الخصوصية داخل WebView.

---

## 🧱 بنية المشروع (Project Structure)

```
com.example.daawahtv/
│
├── MainFragment.kt                # الصفحة الرئيسية مع السلايدرز والبث المباشر والروابط الثابتة
├── PlaybackActivity.kt           # مشغّل الفيديو باستخدام ExoPlayer (يدعم YouTube و M3U8)
├── TvShowDetailsActivity.kt      # عرض تفاصيل البرنامج والحلقات
├── KidsActivity.kt               # صفحة مخصصة لبرامج الأطفال
├── ProgramsWebViewActivity.kt    # خريطة البرامج داخل WebView
├── PrivacyPolicyActivity.kt      # سياسة الخصوصية
│
├── adapters/
│   └── EpisodesAdapter.kt        # محول RecyclerView لعرض الحلقات
│
├── model/
│   ├── HomeResponse.kt           # نموذج بيانات الصفحة الرئيسية (sliders)
│   ├── ProgramItem.kt            # عنصر برنامج واحد
│   ├── TvShowDetailsResponse.kt  # تفاصيل برنامج (title, description, image, etc.)
│   ├── SeasonResponse.kt         # تفاصيل الحلقات حسب الموسم
│   ├── EpisodeItem.kt            # حلقة فردية
│
├── network/
│   ├── ApiClient.kt              # إعداد Retrofit و OkHttpClient
│   ├── ApiService.kt             # دوال جلب بيانات الصفحة الرئيسية والحلقات حسب البرنامج
│   └── TvShowApiService.kt       # دوال جلب تفاصيل برنامج وحلقات الموسم وتفاصيل حلقة
│
├── repository/
│   └── NetworkRepository.kt      # واجهة موحدة للتعامل مع API
│
├── util/
│   └── PlaybackPositionManager.kt # إدارة حفظ واستعادة موضع التشغيل
```

---

## 🌐 الـ API المعتمد

يعتمد على Streamit API من WordPress كالتالي:

### ✅ الصفحة الرئيسية:
```
GET https://daawah.tv/wp-json/streamit/api/v1/content/home
```
يعيد:
- sliders (قوائم البرامج المصنفة)

### ✅ تفاصيل البرنامج:
```
GET https://daawah.tv/wp-json/streamit/api/v1/content/tv-show/{id}
```

### ✅ الحلقات حسب الموسم:
```
GET https://daawah.tv/wp-json/streamit/api/v1/content/tv-show/{tvShowId}/season/{seasonId}?limit=100&offset=0
```

### ✅ تفاصيل الحلقة:
```
GET https://daawah.tv/wp-json/streamit/api/v1/content/episode/{id}
```

### ✅ الحلقات حسب البرنامج:
```
GET https://daawah.tv/wp-json/streamit/api/v1/content/tv-show/season/episodes?program={programId}
```

---

## 📦 المتطلبات

- Android Studio Electric Eel أو أحدث.
- JDK 17.
- Android SDK 33+.
- إنترنت مستقر للوصول إلى API وملفات الفيديو.

---

# 📺 Daawah TV – Android TV App

تطبيق Native Android TV لبث قناة دعوة وعرض البرامج عبر واجهة مبسطة وقابلة للتحكم بالريموت. تم تطويره باستخدام Kotlin في Android Studio، ويعتمد على **Streamit API** من موقع daawah.tv.

---

## 🔧 مميزات التطبيق

- تشغيل البث المباشر لقناة دعوة عبر ExoPlayer.
- عرض البرامج المصنّفة في سلايدر رئيسي.
- عرض تفاصيل البرنامج وقائمة الحلقات.
- دعم الريموت بشكل كامل.
- واجهة بسيطة وسريعة.
- إمكانية التشغيل التلقائي للحلقات المتتالية.
- صفحة مستقلة لبرامج الأطفال.
- سياسة الخصوصية داخل WebView.

---

## 🧱 بنية المشروع (Project Structure)

```
com.example.daawahtv/
│
├── MainFragment.kt                # الصفحة الرئيسية مع السلايدرز والبث المباشر والروابط الثابتة
├── PlaybackActivity.kt           # مشغّل الفيديو باستخدام ExoPlayer (يدعم YouTube و M3U8)
├── TvShowDetailsActivity.kt      # عرض تفاصيل البرنامج والحلقات
├── KidsActivity.kt               # صفحة مخصصة لبرامج الأطفال
├── ProgramsWebViewActivity.kt    # خريطة البرامج داخل WebView
├── PrivacyPolicyActivity.kt      # سياسة الخصوصية
│
├── adapters/
│   └── EpisodesAdapter.kt        # محول RecyclerView لعرض الحلقات
│
├── model/
│   ├── HomeResponse.kt           # نموذج بيانات الصفحة الرئيسية (sliders)
│   ├── ProgramItem.kt            # عنصر برنامج واحد
│   ├── TvShowDetailsResponse.kt  # تفاصيل برنامج (title, description, image, etc.)
│   ├── SeasonResponse.kt         # تفاصيل الحلقات حسب الموسم
│   ├── EpisodeItem.kt            # حلقة فردية
│
├── network/
│   ├── ApiClient.kt              # إعداد Retrofit و OkHttpClient
│   ├── ApiService.kt             # دوال جلب بيانات الصفحة الرئيسية والحلقات حسب البرنامج
│   └── TvShowApiService.kt       # دوال جلب تفاصيل برنامج وحلقات الموسم وتفاصيل حلقة
│
├── repository/
│   └── NetworkRepository.kt      # واجهة موحدة للتعامل مع API
│
├── util/
│   └── PlaybackPositionManager.kt # إدارة حفظ واستعادة موضع التشغيل
```

---

## 🌐 الـ API المعتمد

يعتمد على Streamit API من WordPress كالتالي:

### ✅ الصفحة الرئيسية:
```
GET https://daawah.tv/wp-json/streamit/api/v1/content/home
```
يعيد:
- sliders (قوائم البرامج المصنفة)

### ✅ تفاصيل البرنامج:
```
GET https://daawah.tv/wp-json/streamit/api/v1/content/tv-show/{id}
```

### ✅ الحلقات حسب الموسم:
```
GET https://daawah.tv/wp-json/streamit/api/v1/content/tv-show/{tvShowId}/season/{seasonId}?limit=100&offset=0
```

### ✅ تفاصيل الحلقة:
```
GET https://daawah.tv/wp-json/streamit/api/v1/content/episode/{id}
```

### ✅ الحلقات حسب البرنامج:
```
GET https://daawah.tv/wp-json/streamit/api/v1/content/tv-show/season/episodes?program={programId}
```

---

## 📦 المتطلبات

- Android Studio Electric Eel أو أحدث.
- JDK 17.
- Android SDK 33+.
- إنترنت مستقر للوصول إلى API وملفات الفيديو.

---

## ▶️ طريقة التشغيل

1. افتح المشروع باستخدام Android Studio.
2. تأكد أن `compileSdk` و `targetSdk` هي 33 أو أعلى.
3. شغل المشروع على Android TV Emulator أو جهاز حقيقي.
4. يتم تحميل البيانات تلقائيًا من API عند تشغيل MainFragment.

---

## 📁 المجلدات المحذوفة / غير المستخدمة

تم حذف الملفات التالية لأنها قديمة أو غير متوافقة مع Streamit:

- `DashboardApiService.kt`
- `DashboardResponse.kt`
- `ProgramDetails.kt`
- `ProgramDetailsResponse.kt`
- `EpisodesResponse.kt`
- `EpisodeDetailsResponse.kt`

---

## 🛠 التحديثات القادمة

- إضافة خاصية البحث.
- دعم المفضلة.
- صفحة خاصة بالبرامج الأكثر مشاهدة.
- دعم تحميل الحلقات للمشاهدة لاحقًا.
---

## 🔍 تفاصيل الكود والمكتبات المستخدمة

### 🧱 المكتبات الأساسية:
- `androidx.leanback.*` – لبناء واجهات Android TV (بطاقات، صفوف، خلفيات).
- `androidx.media3.*` – مكتبة Google الحديثة لتشغيل الفيديو (ExoPlayer).
- `retrofit2.*` – مكتبة الاتصال بالشبكة (Networking).
- `okhttp3.*` – إدارة الطلبات والتحكم في الـ Timeout.
- `GsonConverterFactory` – لتحويل JSON إلى كائنات Kotlin والعكس.
- `Glide` – لتحميل الصور من الإنترنت وعرضها بكفاءة عالية.
- `kotlinx.coroutines` – لتشغيل المهام في الخلفية بدون حظر الواجهة.

---

### 📦 تفاصيل الكلاسات والوظائف:

#### `MainFragment.kt`
- المسؤول عن الشاشة الرئيسية في تطبيق Android TV.
- يستخدم `BrowseSupportFragment` لعرض:
  - صفوف البرامج.
  - البث المباشر.
  - سياسة الخصوصية.
  - برامج الأطفال.
- يتعامل مع API `getHomeContent()` لجلب البيانات.
- يحتوي على `onItemClicked` لتوجيه المستخدم إلى الشاشات المناسبة بناءً على نوع البرنامج.

#### `PlaybackActivity.kt`
- يعرض فيديو عبر `ExoPlayer`.
- يتعامل مع HLS (`.m3u8`) أو روابط مباشرة (MP4).
- يدير تشغيل الحلقات التالية أو استئناف التشغيل من آخر موضع.
- يحتوي على منطق للتعامل مع روابط YouTube عبر `YouTubePlayerActivity`.

#### `TvShowDetailsActivity.kt`
- يعرض تفاصيل برنامج تلفزيوني معين.
- الخلفية مأخوذة من صورة البرنامج.
- يظهر العنوان، الوصف، وزر تشغيل.
- يعرض الحلقات في شبكة Grid أسفل الصفحة.
- يتم جلب البيانات من:
  - `getTvShowDetails(id)`
  - `getEpisodesByProgram(id)`

#### `NetworkRepository.kt`
- الواجهة الموحدة لجلب البيانات من الشبكة.
- يفصل بين واجهتي:
  - `ApiService`: يستخدم لصفحة Main وواجهة البرامج.
  - `TvShowApiService`: يستخدم لتفاصيل البرامج والحلقات.

---

### 🛠 الكلاسات المساعدة:

- `Movie.kt` – هيكل البيانات المستخدم داخل التطبيق (كائن يمثل برنامج أو فيديو).
- `CardPresenter.kt` – مسؤول عن عرض كل عنصر في القائمة بشكل مرئي.
- `PlaybackPositionManager.kt` – يتعامل مع حفظ واسترجاع آخر نقطة مشاهدة للحلقات.

---


تفاصيل ملفات مشروع Daawah TV - Android TV (Kotlin)
هذا المستند يحتوي على شرح تفصيلي لجميع الملفات المصدرية والطبقات البرمجية المستخدمة في مشروع تطبيق Android TV الخاص بقناة دعوة،
والذي يعتمد على Kotlin، ExoPlayer، وواجهة برمجية REST من WordPress API (قالب Streamit).
MainFragment.kt
الشاشة الرئيسية للتطبيق.
- ترث من BrowseSupportFragment لعرض صفوف المحتوى على واجهة Android TV.
- تقوم بجلب بيانات الصفحة الرئيسية من API باستخدام NetworkRepository.
- تنشئ صفًا ثابتًا يحتوي على (البث المباشر، خريطة البرامج، سياسة الخصوصية، برامج الأطفال).
- تنشئ صفوفًا ديناميكية من السلايدرات القادمة من API.
- يتم التعامل مع خلفية الشاشة باستخدام Glide.
- تدير التنقل إلى الأنشطة المختلفة عند الضغط على كل عنصر.
TvShowDetailsActivity.kt
تعرض تفاصيل برنامج تلفزيوني.
- تستخدم Retrofit لجلب تفاصيل البرنامج والحلقات بناءً على ID البرنامج.
- تعرض صورة خلفية البرنامج (featured image)، والعنوان، والوصف المختصر.
- تعرض الحلقات بشكل Grid باستخدام RecyclerView.
- عند الضغط على الحلقة يتم التنقل إلى PlaybackActivity أو YouTubePlayerActivity حسب نوع الرابط.
PlaybackActivity.kt
تشغيل الفيديو باستخدام ExoPlayer.
- يدعم التشغيل التلقائي للحلقة التالية عند نهاية الحلقة الحالية.
- يدعم التشغيل بصيغة HLS (m3u8) أو Progressive.
- يخزن ويستعيد موضع التشغيل باستخدام PlaybackPositionManager.
- يدعم التشغيل من قائمة حلقات.
ApiClient.kt
تهيئة Retrofit.
- يحتوي على Retrofit singleton مخصص لربط apiService وtvShowApiService.
- يستخدم OkHttpClient مع timeouts مخصصة.
- عنوان الـ baseUrl هو https://daawah.tv/wp-json/streamit/api/v1/content/
ApiService.kt
واجهة API الرئيسية.
- getHomeContent: لجلب محتوى الصفحة الرئيسية.
- getEpisodesByProgram: لجلب الحلقات المرتبطة ببرنامج معين.
TvShowApiService.kt
واجهة API لطلبات البرامج التفصيلية.
- getTvShowDetails: لجلب تفاصيل البرنامج.
- getSeasonEpisodes: لجلب الحلقات لموسم معين.
- getEpisodeDetails: لجلب تفاصيل حلقة معينة.
NetworkRepository.kt
طبقة Repository المسؤولة عن تنفيذ طلبات API باستخدام coroutines.
- تتعامل مع كل من ApiService و TvShowApiService.
- تحتوي على دوال لكل عملية: getHomeContent, getTvShowDetails, getSeasonEpisodes, getEpisodeDetails, getEpisodesByProgram.
Episode.kt و EpisodeItem.kt
نموذج بيانات لحلقة واحدة.
- يحتوي على: id, title, image, duration, videoUrl.
- EpisodeItem يستخدم غالبًا للعرض في RecyclerView.
ProgramItem.kt
نموذج بيانات يمثل برنامج تلفزيوني.
- يحتوي على: id, title, image, post_type.
- يُستخدم لإنشاء عناصر Movie التي تُعرض في MainFragment.
HomeResponse.kt
نموذج يحتوي على استجابة الصفحة الرئيسية.
- يحتوي على قائمة من الـ sliders.
- كل slider يحتوي على عنوان وقائمة من البرامج (ProgramItem).
PlaybackPositionManager.kt
أداة مساعدة لتخزين واسترجاع موضع التشغيل لكل حلقة.
- يُخزن الموضع في SharedPreferences.
- تُستخدم في PlaybackActivity لتتبع التقدم في الفيديو.
CardPresenter.kt
مسؤول عن عرض بطاقات المحتوى في Android TV.
- يُستخدم لتصميم واجهة البطاقة (صورة وعنوان ووصف).
- يُستخدم في MainFragment وTvShowDetailsActivity.
KidsActivity.kt
شاشة فرعية لعرض برامج الأطفال.
- يتم فتحها عند الضغط على "برامج الأطفال" في MainFragment.
- يمكنك تخصيصها لاحقًا لعرض محتوى من API.
ProgramsWebViewActivity.kt
شاشة فرعية تستخدم WebView لعرض خريطة البرامج.
- تعرض صفحة ويب من موقع دعوة.
PrivacyPolicyActivity.kt
تعرض سياسة الخصوصية في WebView.
- تُستخدم لعرض رابط ثابت للخصوصية.

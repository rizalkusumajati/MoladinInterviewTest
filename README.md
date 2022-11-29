# MoladinInterviewTest Improvement

| Improvement  | Before | After  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| Architecture  | <img width="286" alt="Screen Shot 2022-11-29 at 09 51 57" src="https://user-images.githubusercontent.com/19677893/204430592-aae1f845-4b5e-4d0e-9f65-a1d763dbc404.png"> | <img width="312" alt="Screen Shot 2022-11-29 at 09 53 12" src="https://user-images.githubusercontent.com/19677893/204430644-372861d6-0606-4e83-b431-a78687d94e5e.png"> | Improve using 3 layers (data, domain, presentation/UI) for app Architecture <br> https://developer.android.com/topic/architecture |
| Clean Code  | -  | MVVM  | Using MVVM as a Design Pattern <br> Using hilt for dependency injection |
| UI  | <img width="357" alt="Screen Shot 2022-11-29 at 09 57 58" src="https://user-images.githubusercontent.com/19677893/204431451-a54817bb-706e-46fe-bec4-57b822d37b4f.png"> | ![image](https://user-images.githubusercontent.com/19677893/204431525-596bfdd4-860e-4086-a757-66d8321ff815.png) <br> <img width="351" alt="Screen Shot 2022-11-29 at 10 17 13" src="https://user-images.githubusercontent.com/19677893/204431590-6eb4dcf1-62ef-4d08-8945-ad7bcc8dead3.png">  | Improve UI show avatar, first name, last name, and email of user in cardview, also add Loading State and error state layout view |
| Security  | ![image](https://user-images.githubusercontent.com/19677893/204431822-274240a9-8ae8-4e2c-81b9-76a6e2cc6b17.png) | ![image](https://user-images.githubusercontent.com/19677893/204431875-91465a4e-8574-46c4-90b5-0ec68f5f3ad0.png)| Using proguard in release build |
| UnitTest  | -  | ![image](https://user-images.githubusercontent.com/19677893/204432009-3187d445-1f3f-496d-866f-16f0d90d35fc.png) | Add Unit Test |
| Pagination  | -  | Paging3 Library  | Using Paging 3 Library to handle pagination <br>https://developer.android.com/topic/libraries/architecture/paging/v3-overview |
| HttpLogging  | ![image](https://user-images.githubusercontent.com/19677893/204432401-01b42ca4-33b5-4afc-bc54-e65d15b35195.png)  | ![image](https://user-images.githubusercontent.com/19677893/204432453-68636fb6-7418-46bd-a91f-88e26cb4e2e8.png)  | Logging Level Body for debug only|
| Glide Optimization  | -  | ![image](https://user-images.githubusercontent.com/19677893/204432651-925428a8-20d2-4e22-a260-6f8f7ce7edaf.png)  | implement diskCacheStrategy for load avatar |

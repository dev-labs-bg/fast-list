![logo](https://raw.githubusercontent.com/dev-labs-bg/fast-list/master/logo.png)


Create dynamic, fast and easy recycler view lists (including ViewPager2). **No adapters, no view holders**.

[![Download](https://img.shields.io/badge/download-1.2-6db33f.svg?style=flat-square&label=version)](https://jitpack.io/#dev-labs-bg/fast-list) [![Twitter URL](https://img.shields.io/badge/twitter-%40devlabsbg-1DA1F2.svg?style=flat-square&logo=twitter)](http://twitter.com/devlabsbg)


## Usage

FastList supports 2 types of lists- single layout lists and dynamic lists.

- Here's how to create a simple single layout list:

```kotlin
        val list = listOf(Item("fast", 1), Item("recycler", 2), Item("view", 1))

        recycler_view.bind(list, R.layout.item) { it : Item ->
            item_text.text = it.value
        }
```
That's it (for ViewPager2, just replace recycler_view with the ViewPager2 instance)! The first parameter is the list you want to show, the second is the ID of the layout and the third one is a function for binding each element. It uses Kotlin Extensions, so you can directly address the XML views and set them up.


- The second type is dynamic lists with multiple layouts:

```kotlin
        val list = listOf(Item("fast", 1), Item("recycler", 2), Item("view", 1))

        recycler_view.bind(list)
                .map(layout = R.layout.item, predicate = { it.type == 1}) {
                    item_text.text = it.value
                }
                .map(layout = R.layout.item_second, predicate = { it.type == 2}) {
                    item_second_text.text = it.value
                }
                .layoutManager(LinearLayoutManager(this))
```
The map function accepts 3 parameters. The first is the ID of the layout for the type. The second is the predicate function by which you want to sort your items. The last one is the "view holder" binding function for each element. It uses Kotlin Extensions, so you can directly address the XML views and set them up.

If you need control over your view's creation, you can pass a factory that allows you to create your own view:
```kotlin
        val list = listOf(Item("fast", 1), Item("recycler", 2), Item("view", 1))

        recycler_view.bind(list)
                .map(layoutFactory = LocalFactory(this), predicate = { it.type == 1}) {
                    item_text.text = it.value
                }
                .map(layout = R.layout.item_second, predicate = { it.type == 2}) {
                    item_second_text.text = it.value
                }
                .layoutManager(LinearLayoutManager(this))
				...
	class LocalFactory(val activity: AppCompatActivity) : LayoutFactory {
		override fun createView(parent: ViewGroup, type: Int): View {
			val view = LayoutInflater.from(activity).inflate(R.layout.item,
					parent, false)
			//Manipulate view as needed 
			return view		
		}
	}				
```



You can also update a list in a shown recycler view with this DiffUtils update function:
```kotlin
        ...
        val list2 = listOf(Item("fast", 1))

        recycler_view.update(list2)
```

---
## Download

### Manually

You can also manually download [the library class](https://github.com/dev-labs-bg/fast-list/blob/master/fast-list/src/main/java/com/list/rados/fast_list/BaseList.kt) and use it in your application.

**Important**: For the manual download you need Kotlin extensions to be enabled. To turn them on, simply place "apply plugin: 'kotlin-android-extensions'" inside of your app build.gradle


### Gradle

```gradle
dependencies {
  implementation 'bg.devlabs.fastlist:fast-list:$latest_version'

}
 ```
 
### Maven
```xml
<dependency>
  <groupId>bg.devlabs.fastlist</groupId>
  <artifactId>fast-list</artifactId>
  <version>latest_version</version>
  <type>pom</type>
</dependency>
```

---
## Compatibility

Minimum Android SDK: API level 19

---
## Author

Radoslav Yankov [@Radoslav_Y](https://twitter.com/Radoslav_Y)

---
## Getting help

If you spot a problem you can open an issue on the Github page, or alternatively, you can tweet us at [@devlabsbg](https://twitter.com/devlabsbg)

---
## License

FastList is released under the [MIT License](https://github.com/dev-labs-bg/fast-list/blob/master/LICENSE).

![logo](https://raw.githubusercontent.com/dev-labs-bg/fast-list/master/logo.png)


Create dynamic, fast and easy recycler view lists (including ViewPager2). **No adapters, no view holders**.

[![Download](https://img.shields.io/badge/version-1.4-6db33f?style=flat-square)](https://jitpack.io/#dev-labs-bg/fast-list) [![Twitter URL](https://img.shields.io/badge/twitter-%40devlabsbg-1DA1F2.svg?style=flat-square&logo=twitter)](http://twitter.com/devlabsbg)


## Usage

FastList supports 2 types of lists- single layout lists and dynamic lists.

- Here's how to create a simple single layout list:

```kotlin
        val list = listOf(Item("fast", 1), Item("recycler", 2), Item("view", 1))

        recycler_view.bind(list, R.layout.item) { it : Item, position: Int ->
            item_text.text = it.value
        }
```
That's it (for ViewPager2, just replace recycler_view with the ViewPager2 instance)! The first parameter is the list you want to show, the second is the ID of the layout and the third one is a function for binding each element. It uses Kotlin Extensions, so you can directly address the XML views and set them up.


- The second type is dynamic lists with multiple layouts:

```kotlin
        val list = listOf(Item("fast", 1), Item("recycler", 2), Item("view", 1))

        recycler_view.bind(list)
                .map(layout = R.layout.item, predicate = { it: Item, _ -> it.type == 1}) { item: Item, p: Int ->
                    item_text.text = it.value
                }
                .map(layout = R.layout.item_second, predicate = { it: Item, _ -> it.type == 2}) { item: Item, p: Int ->
                    item_second_text.text = it.value
                }
                .layoutManager(LinearLayoutManager(this))
```
The map function accepts 3 parameters. The first is the ID of the layout for the type. The second is the predicate function by which you want to sort your items. The last one is the "view holder" binding function for each element. It uses Kotlin Extensions, so you can directly address the XML views and set them up.

If you need control over your view's creation, you can pass a factory that allows you to create your own view:
```kotlin
        val list = listOf(Item("fast", 1), Item("recycler", 2), Item("view", 1))

        recycler_view.bind(list)
                .map(layoutFactory = LocalFactory(this), predicate = { it: Item, _ -> it.type == 1}) { item: Item, p: Int ->
                    item_text.text = it.value
                }
                .map(layout = R.layout.item_second, predicate = { it: Item, _ -> it.type == 2}) { item: Item, p: Int ->
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

## Download

### Manually

The recommended way to download is to copy the [the library class](https://github.com/dev-labs-bg/fast-list/blob/master/fast-list/src/main/java/com/list/rados/fast_list/BaseList.kt) and use it in your application.

**Important**: For the manual download you need Kotlin extensions to be enabled. To turn them on, simply place "apply plugin: 'kotlin-android-extensions'" inside of your app build.gradle

### Gradle

```gradle
dependencies {
  implementation 'bg.devlabs.fastlist:fast-list:$latest_version'
}
 ```

## Getting help

Dev Labs [@devlabsbg](https://twitter.com/devlabsbg)

Radoslav Yankov [@rado__yankov](https://twitter.com/rado__yankov/)

Under [MIT License](https://github.com/dev-labs-bg/fast-list/blob/master/LICENSE).

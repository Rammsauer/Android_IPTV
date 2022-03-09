# Android IPTV
## Description
Player for a list of IPTV

## Database 
The App stores all data in a local SQLLight Database by using room. So its not necessary to reload the whole List after initialising it by the first launch.
<br>

´´´ kotlin
@Entity
data class Channel(
@PrimaryKey(autoGenerate = true)                            val uid         : Int = 0,
@ColumnInfo(name = "name")                                  val name        : String?,
@ColumnInfo(name = "url")                                   val url         : String?,
@ColumnInfo(name = "logo", typeAffinity = ColumnInfo.BLOB)  val logo        : ByteArray? = null,
@ColumnInfo(name = "country")                               val group       : String?,
@ColumnInfo(name = "onlineStatus")                          val status      : Int = 0,
)
´´´

### Network

## Used Software
[Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
<br>
[Dagger](https://dagger.dev/)
<br>
[Room](https://developer.android.com/training/data-storage/room)

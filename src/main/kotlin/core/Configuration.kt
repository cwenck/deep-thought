package core

object Configuration {
    val botToken: String?
        get() = System.getenv("BOT_TOKEN")
}

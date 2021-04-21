rootProject.name = "hibernate-demo"

pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "org.hibernate.orm") {
                useModule("org.hibernate:hibernate-gradle-plugin:${requested.version}")
            }
        }
    }
}

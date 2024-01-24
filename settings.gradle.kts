rootProject.name = "MyProject"
include("HibernateService")
include("HibernateService:service")
findProject(":HibernateService:service")?.name = "service"
include("HibernateService:dao")
findProject(":HibernateService:dao")?.name = "dao"
include("SpringBootRestfulService")
include("SpringBootRestfulService:dao")
findProject(":SpringBootRestfulService:dao")?.name = "dao"
include("SpringBootRestfulService:service")
findProject(":SpringBootRestfulService:service")?.name = "service"
include("SpringBootRestfulService:controller")
findProject(":SpringBootRestfulService:controller")?.name = "controller"
include("MicroserviceApplication")
include("MicroserviceApplication:catMicroservice")
findProject(":MicroserviceApplication:catMicroservice")?.name = "catMicroservice"
include("MicroserviceApplication:ownerMicroservice")
findProject(":MicroserviceApplication:ownerMicroservice")?.name = "ownerMicroservice"
include("MicroserviceApplication:externalMicroservice")
findProject(":MicroserviceApplication:externalMicroservice")?.name = "externalMicroservice"
include("MicroserviceApplication:commonDaoAndDto")
findProject(":MicroserviceApplication:commonDaoAndDto")?.name = "commonDaoAndDto"

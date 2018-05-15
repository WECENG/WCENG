SpringBoot-AMQP
        1.使用RabbitMQ消息代理
                a.RabbitMQ是在erlang语言环境下运行的，所以需要先安装erlang环境
                    下载.exe包后，要以管理员的身份运行(确保注册表中存在安装信息)
                    不然RabbitMQ安装会报错本机上不存在erlang运行环境
                b.安装RabbitMQ,安装完成后在开始菜单栏中会出现四个快捷方式
                    1.RabbitMQ Service -(re)install  启动服务
                    2.RabbitMQ Service -Stop
                    3.RabbitMQ Service -Start
                    4.RabbitMQ Service -Remove
         2.AMQP使用RabbitMQ消息代理的工作流程如图AMQP.png
                注意：虽然监听容器设置的监听目的地是queueName,但是他的值是要与
                        routing key 一致的。       
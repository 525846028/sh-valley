#!/bin/bash

# 随机制造CPU负载
# 使用方法 sh cpu_chaos.sh 2>&1 &

function rand(){
    min=$1
    max=$(($2-$min+1))
    num=$(($RANDOM+1000000000))
    echo $(($num%$max+$min))
}

while((1))
do
    echo CPU Chaos Start at `date`
    rnd=$(rand 20 100)  #启动dd进程的数量，根据CPU个数适当调整
    srnd=$(rand 20 200) #dd进程运行的时间长度，从20秒到200秒
    crnd=$(rand 1 1000) #开始下一次CHAOS的间隔，从1秒到1000秒

    echo CPU Chaos DD Count $rnd
    echo CPU Chaos DD Duration $srnd
    echo CPU Chaos Next DD Duration $crnd

    for i in `seq $rnd`
    do
        dd if=/dev/zero of=/dev/null&
    done

    sleep $srnd
    echo CPU Chaos CPU Load `uptime`
    ps -ef | grep zero | awk '{print $2}' | xargs kill

    sleep $crnd
    echo CPU Chaos End at `date`
done
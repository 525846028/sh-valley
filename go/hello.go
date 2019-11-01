package main

import "fmt"

func main() {
	i, j, l, m := 1, 2, 3, 4

	fmt.Println("Hello go world")

	fmt.Println(i)
	fmt.Println(j)
	fmt.Println(l)
	fmt.Println(m)

	ch := make(chan int, 1)
	for {
		select {
			case ch <- 0:
			case ch <- 1:
		}

		i := <- ch
		fmt.Println("Value Received: " , i)
	}
}
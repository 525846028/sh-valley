package main

import (
	"io"
	"log"
	"net/http"
	"os"
	// "bytes"
	"fmt"
)

func helloHandler(w http.ResponseWriter, r *http.Request){
	io.WriteString(w, "Hello from server")
}

func main() {
	// if( len(os.Args) != 2){
		fmt.Println("Usage: ", os.Args[0], " port")
	// 	os.Exit(1)
	// }
	// port := os.Args[1]

	http.HandleFunc("/hello", helloHandler)
	err := http.ListenAndServe(":8080", nil)

	if err != nil {
		log.Fatal("ListenAndServe: " , err.Error())
	}
}
 docker build -t session_storage .
 docker run -p 8453:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres  session_storage
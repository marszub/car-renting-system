# syntax=docker/dockerfile:1.4
FROM mcr.microsoft.com/dotnet/sdk:6.0-focal AS environment
WORKDIR /source
RUN apt-get update
RUN apt-get -y install zeroc-ice-all-runtime zeroc-ice-all-dev

FROM environment AS build
WORKDIR /source
# copy csproj and restore as distinct layers
COPY Auth/*.csproj ./Auth/
RUN dotnet restore Auth/Auth.csproj
# copy everything else and build app
COPY Auth/. ./Auth/
WORKDIR /
COPY --from=slice . ./shared/slice
WORKDIR /source/Auth
RUN dotnet build -c release --no-restore

FROM build AS publish
RUN dotnet publish -c release --no-build -o /app

# final stage/image
FROM mcr.microsoft.com/dotnet/aspnet:6.0
WORKDIR /app
COPY --from=publish /app .
ENTRYPOINT ["dotnet", "Auth.dll"]
CMD ["/config/auth.ice", "/config/auth.json"]
EXPOSE 5010/tcp
EXPOSE 5012/tcp
EXPOSE 5012/udp
VOLUME /config

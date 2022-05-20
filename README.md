<div id="top"></div>

<br />
<div align="center">
    <img src="auction.png" alt="Logo" width="80" height="80">

  <h3 align="center">Implantation d'un système de gestion d'enchères en BCM4JAVA</h3>

  <p align="center">
  UE PSTL
    <br/>
  </p>
</div>

#### Développé par

Malik Bouaoud

Sandrine Ear

#### Encadrant  
Jacques Malenfant

## Lancement du projet

Ouvrir Eclipse, et inclure tout les jars dans le **Build path**

### 1. MONO JVM

Afin de d'executer le projet en mono JVM, il suffit de lancer les deux classes suivantes :

> auctions/src/cvms/CVMEnglishBidding.java

> auctions/src/cvms/CVMThreshold.java


### 2. Multi JVM

Ouvrir 4 terminaux et se placer dans le dossier **auctions/src/dcvms**

#### 2.1 Lancer la barière cyclique
> bash start-cb.sh

#### 2.2 Lancer le registre global
> bash start-global-register.sh

#### 2.3 Lancer les JVMs

> bash  start-dcvm.sh DistributedCVMThreshold.java JVM1

> bash start-dcvm.sh DistributedCVMThreshold.java JVM2


### 3. Documentation

La documentation de notre système d'enchère se trouve dans **Auction/Doc**

#### 4.Architecture des paquets

````
.
├── components
│   ├── englishAuction
│   │   ├── EnglishAuctioneer.java
│   │   ├── EnglishBidder.java
│   │   ├── EnglishSeller.java
│   │   ├── connections
│   │   │   ├── connectors
│   │   │   │   ├── EnglishAuctioneerConnector.java
│   │   │   │   ├── EnglishBidderConnector.java
│   │   │   │   └── EnglishSellerConnector.java
│   │   │   ├── ibp
│   │   │   │   ├── EnglishAuctioneerInboundPort.java
│   │   │   │   ├── EnglishBidderInboundPort.java
│   │   │   │   └── EnglishSellerInboundPort.java
│   │   │   └── obp
│   │   │       ├── EnglishAuctioneerOutboundPort.java
│   │   │       ├── EnglishBidderOutboundPort.java
│   │   │       └── EnglishSellerOutboundPort.java
│   │   ├── interfaces
│   │   │   ├── EnglishAuctioneerCI.java
│   │   │   ├── EnglishBidderCI.java
│   │   │   ├── EnglishSellerCI.java
│   │   │   └── package-info.java
│   │   └── package-info.java
│   ├── interfaces
│   │   ├── ProtocolCI.java
│   │   └── package-info.java
│   ├── package-info.java
│   └── threshold
│       ├── ThresholdAuctioneer.java
│       ├── ThresholdBidder.java
│       ├── ThresholdSeller.java
│       ├── connections
│       │   ├── connectors
│       │   │   ├── ThresholdAuctioneerConnector.java
│       │   │   ├── ThresholdBidderConnector.java
│       │   │   └── ThresholdSellerConnector.java
│       │   ├── ibp
│       │   │   ├── ThresholdAuctioneerInboundPort.java
│       │   │   ├── ThresholdBidderInboundPort.java
│       │   │   └── ThresholdSellerInboundPort.java
│       │   └── obp
│       │       ├── ThresholdAuctioneerOutboundPort.java
│       │       ├── ThresholdBidderOutboundPort.java
│       │       └── ThresholdSellerOutboundPort.java
│       ├── interfaces
│       │   ├── ThresholdAuctioneerCI.java
│       │   ├── ThresholdBidderCI.java
│       │   ├── ThresholdSellerCI.java
│       │   └── package-info.java
│       └── package-info.java
├── cvms
│   ├── CVMEnglishBidding.java
│   └── CVMThreshold.java
├── dcvm
│   ├── DistributedCVMThreshold.java
│   ├── config
│   │   └── deployment.rnc
│   ├── config.xml
│   ├── cyclicBarrier.log
│   ├── dcvm.policy
│   ├── globalRegistry.log
│   ├── jars
│   │   ├── BCM4Java-20092021.jar
│   │   ├── Front.jar
│   │   ├── automaton-1.11-8.jar
│   │   ├── commons-lang3-3.5.jar
│   │   ├── generex-1.0.2.jar
│   │   ├── javafaker-1.0.2.jar
│   │   ├── javassist.jar
│   │   ├── jing.jar
│   │   └── snakeyaml-1.23-android.jar
│   ├── start-cb.sh
│   ├── start-dcvm.sh
│   └── start-global-register.sh
├── entities
│   ├── Behavior.java
│   ├── BiddedObject.java
│   ├── Offer.java
│   ├── ProtocolProgress.java
│   └── ProtocolState.java
└── plugins
    ├── englishAuction
    │   ├── EnglishAuctioneerPlugin.java
    │   ├── EnglishBidderPlugin.java
    │   ├── EnglishSellerPlugin.java
    │   └── ports
    │       ├── EnglishAuctioneerInboundPortForPlugin.java
    │       ├── EnglishBidderInboundPortForPlugin.java
    │       └── EnglishSellerInboundPortForPlugin.java
    └── threshold
        ├── ThresholdAuctioneerPlugin.java
        ├── ThresholdBidderPlugin.java
        ├── ThresholdSellerPlugin.java
        └── ports
            ├── ThresholdAuctioneerInboundPortForPlugin.java
            ├── ThresholdBidderInboundPortForPlugin.java
            └── ThresholdSellerInboundPortForPlugin.java

````

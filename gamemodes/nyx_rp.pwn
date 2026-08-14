#include <a_samp>

#define NYX_NAME "NYX ROLEPLAY"
#define COLOR_NYX 0x8B5CF6FF
#define COLOR_WHITE 0xFFFFFFFF
#define COLOR_GREEN 0x35D07FFF
#define COLOR_RED 0xFF4D5AFF
#define COLOR_YELLOW 0xFFD166FF
#define COLOR_BLUE 0x4DA3FFFF
#define INVALID_ORG -1
#define INVALID_JOB -1
#define D_LOGIN 1000
#define D_REGISTER 1001
#define D_MAIN 1002
#define D_JOBS 1003
#define D_ORGS 1004
#define D_SKIN 1005
#define D_PROPERTY 1006
#define D_AUCTION 1007
#define D_ADMIN 1008
#define D_STORE 1009
#define MAX_PROPERTIES 40
#define MAX_AUCTIONS 12

new bool:gLogged[MAX_PLAYERS];
new gAdmin[MAX_PLAYERS];
new bool:gAdminInvisible[MAX_PLAYERS];
new gOrg[MAX_PLAYERS];
new gJob[MAX_PLAYERS];
new gMoney[MAX_PLAYERS];
new gNCoins[MAX_PLAYERS];
new gSkin[MAX_PLAYERS];
new gMarriage[MAX_PLAYERS];
new gPendingMarriage[MAX_PLAYERS];
new gFamily[MAX_PLAYERS];
new gFishing[MAX_PLAYERS];
new gFishCount[MAX_PLAYERS];
new gJobCheckpoint[MAX_PLAYERS];
new gJobVehicle[MAX_PLAYERS];
new gProperty[MAX_PLAYERS];

new const OrgName[24][] = {
    "Policia Militar", "Policia Civil", "Policia Federal", "BOPE",
    "Forcas Armadas", "Exercito Brasileiro", "Marinha do Brasil", "Forca Aerea Brasileira",
    "Guarda Municipal", "SAMU", "Corpo de Bombeiros", "Governo",
    "Prefeitura NYX", "Jornal NYX", "Mecanicos", "Transportes NYX",
    "Ballas", "Families", "Comando Vermelho", "Primeiro Comando",
    "Los Aztecas", "Cartel NYX", "Motoclube", "Sindicato do Crime"
};

new const JobName[15][] = {
    "Construcao / Obra", "Minerador", "Fazendeiro", "Frentista", "Gasista",
    "Motoboy", "Caminhoneiro", "Taxista", "Motorista de Onibus", "Pescador",
    "Eletricista", "Entregador", "Coletor de Lixo", "Mecanico", "Agricultor"
};
new const JobPay[15] = {1800,2100,1700,1600,1900,2000,2600,1900,2300,1800,2000,1900,2200,2400,2000};
new const JobVehicleModel[15] = {0,486,531,466,524,462,403,420,431,453,0,482,408,525,478};

new const CityName[5][] = {"Sao Paulo", "Goiania", "Curitiba", "Blumenau", "Florianopolis"};
new const CityX[5] = {1480, 2100, 1650, 1800, 1950};
new const CityY[5] = {-1730, -1700, -1800, -1650, -1750};

new PropertyOwner[MAX_PROPERTIES];
new PropertyPrice[MAX_PROPERTIES];
new PropertyType[MAX_PROPERTIES][32];
new PropertyName[MAX_PROPERTIES][48];
new AuctionProperty[MAX_AUCTIONS];
new AuctionBid[MAX_AUCTIONS];
new AuctionBidder[MAX_AUCTIONS];

stock ResetPlayerNYX(playerid)
{
    gLogged[playerid] = false;
    gAdmin[playerid] = 0;
    gAdminInvisible[playerid] = false;
    gOrg[playerid] = INVALID_ORG;
    gJob[playerid] = INVALID_JOB;
    gMoney[playerid] = 5000;
    gNCoins[playerid] = 0;
    gSkin[playerid] = 1;
    gMarriage[playerid] = INVALID_PLAYER_ID;
    gPendingMarriage[playerid] = INVALID_PLAYER_ID;
    gFamily[playerid] = -1;
    gFishing[playerid] = 0;
    gFishCount[playerid] = 0;
    gJobCheckpoint[playerid] = 0;
    gJobVehicle[playerid] = INVALID_VEHICLE_ID;
    gProperty[playerid] = -1;
    return 1;
}

stock GetNYXPlayerName(playerid, name[], size)
{
    GetPlayerName(playerid, name, size);
    return 1;
}

stock GiveNYXMoney(playerid, amount)
{
    gMoney[playerid] += amount;
    ResetPlayerMoney(playerid);
    GivePlayerMoney(playerid, gMoney[playerid]);
    return 1;
}

stock SendOrgMessage(playerid, const text[])
{
    if(gOrg[playerid] == INVALID_ORG) return SendClientMessage(playerid, COLOR_RED, "Voce nao pertence a uma organizacao.");
    for(new i = 0; i < MAX_PLAYERS; i++)
        if(IsPlayerConnected(i) && gOrg[i] == gOrg[playerid]) SendClientMessage(i, COLOR_BLUE, text);
    return 1;
}

stock SendAdminMessage(const text[])
{
    for(new i = 0; i < MAX_PLAYERS; i++)
        if(IsPlayerConnected(i) && gAdmin[i] > 0) SendClientMessage(i, COLOR_RED, text);
    return 1;
}

stock HideAdminNameTag(playerid, bool:hide)
{
    for(new i = 0; i < MAX_PLAYERS; i++)
        if(IsPlayerConnected(i) && i != playerid) ShowPlayerNameTagForPlayer(i, playerid, !hide);
    return 1;
}

stock CreateNYXProperties()
{
    for(new i = 0; i < MAX_PROPERTIES; i++)
    {
        PropertyOwner[i] = INVALID_PLAYER_ID;
        PropertyPrice[i] = 0;
        PropertyType[i][0] = EOS;
        PropertyName[i][0] = EOS;
    }
    new id = 0;
    for(new i = 0; i < 10; i++) { format(PropertyName[id],48,"Casa NYX %d",i+1); format(PropertyType[id],32,"Casa"); PropertyPrice[id]=25000+(i*5000); id++; }
    for(new i = 0; i < 10; i++) { format(PropertyName[id],48,"Apartamento NYX %d",i+1); format(PropertyType[id],32,"Apartamento"); PropertyPrice[id]=65000+(i*10000); id++; }
    for(new i = 0; i < 8; i++) { format(PropertyName[id],48,"Mansao NYX %d",i+1); format(PropertyType[id],32,"Mansao"); PropertyPrice[id]=500000+(i*100000); id++; }
    for(new i = 0; i < 6; i++) { format(PropertyName[id],48,"Cobertura NYX %d",i+1); format(PropertyType[id],32,"Cobertura"); PropertyPrice[id]=350000+(i*75000); id++; }
    return 1;
}

stock CreateNYXAuctions()
{
    for(new i = 0; i < MAX_AUCTIONS; i++)
    {
        AuctionProperty[i] = i;
        AuctionBid[i] = PropertyPrice[i];
        AuctionBidder[i] = INVALID_PLAYER_ID;
    }
    return 1;
}

public OnGameModeInit()
{
    SetGameModeText(NYX_NAME);
    ShowPlayerMarkers(1);
    ShowNameTags(1);
    UsePlayerPedAnims();
    SetWorldTime(12);
    SetWeather(10);
    CreateNYXProperties();
    CreateNYXAuctions();

    AddPlayerClass(1,1480.0,-1730.0,13.5,0.0,0,0,0,0,0,0);
    AddPlayerClass(2,1480.0,-1730.0,13.5,0.0,0,0,0,0,0,0);

    Create3DTextLabel("{8B5CF6}NYX ROLEPLAY\\n{FFFFFF}Prefeitura Central",COLOR_WHITE,1480.0,-1730.0,15.0,40.0,0,1);
    Create3DTextLabel("{35D07F}HOSPITAL CENTRAL NYX",COLOR_WHITE,1520.0,-1675.0,15.0,40.0,0,1);
    Create3DTextLabel("{4DA3FF}DELEGACIA CENTRAL NYX",COLOR_WHITE,1550.0,-1600.0,15.0,40.0,0,1);
    Create3DTextLabel("{FFD166}SHOPPING NYX | ROUPAS",COLOR_WHITE,1420.0,-1800.0,15.0,40.0,0,1);
    Create3DTextLabel("{FF4D5A}CINEMA NYX",COLOR_WHITE,1380.0,-1820.0,15.0,40.0,0,1);
    Create3DTextLabel("{8B5CF6}IGREJA CENTRAL NYX",COLOR_WHITE,1340.0,-1760.0,15.0,40.0,0,1);
    Create3DTextLabel("{FFD166}CASINO NYX",COLOR_WHITE,2200.0,-1670.0,16.0,40.0,0,1);
    Create3DTextLabel("{FF4D5A}SEX SHOP NYX",COLOR_WHITE,1350.0,-1740.0,15.0,40.0,0,1);
    Create3DTextLabel("{4DA3FF}PORTAL MONUMENTAL NYX",COLOR_WHITE,1700.0,-1500.0,20.0,50.0,0,1);
    print("[NYX] Core standalone SA-MP iniciado.");
    return 1;
}

public OnPlayerConnect(playerid)
{
    ResetPlayerNYX(playerid);
    ShowPlayerDialog(playerid,D_LOGIN,DIALOG_STYLE_PASSWORD,"NYX ROLEPLAY | LOGIN","Bem-vindo a NYX ROLEPLAY.\\n\\nDigite sua senha.","ENTRAR","REGISTRAR");
    return 1;
}

public OnPlayerDisconnect(playerid, reason)
{
    if(gJobVehicle[playerid] != INVALID_VEHICLE_ID) DestroyVehicle(gJobVehicle[playerid]);
    return 1;
}

public OnPlayerRequestClass(playerid, classid)
{
    gSkin[playerid] = (classid == 0) ? 1 : 2;
    SetPlayerSkin(playerid, gSkin[playerid]);
    SetPlayerCameraPos(playerid,1488.0,-1757.0,24.0);
    SetPlayerCameraLookAt(playerid,1480.0,-1730.0,13.5);
    return 1;
}

public OnPlayerSpawn(playerid)
{
    if(!gLogged[playerid]) return 1;
    SetPlayerSkin(playerid, gSkin[playerid]);
    ResetPlayerMoney(playerid);
    GivePlayerMoney(playerid, gMoney[playerid]);
    SetPlayerPos(playerid,1480.0,-1730.0,13.5);
    SetPlayerInterior(playerid,0);
    return 1;
}

public OnPlayerDeath(playerid, killerid, reason)
{
    gFishing[playerid] = 0;
    gJobCheckpoint[playerid] = 0;
    DisablePlayerCheckpoint(playerid);
    return 1;
}

public OnPlayerEnterCheckpoint(playerid)
{
    if(gFishing[playerid])
    {
        gFishCount[playerid] += random(4) + 1;
        DisablePlayerCheckpoint(playerid);
        SendClientMessage(playerid,COLOR_GREEN,"Pesca concluida. Use /venderpeixe para vender sua captura.");
        return 1;
    }
    if(gJobCheckpoint[playerid])
    {
        new job = gJob[playerid];
        gJobCheckpoint[playerid] = 0;
        DisablePlayerCheckpoint(playerid);
        GiveNYXMoney(playerid, JobPay[job]);
        SendClientMessage(playerid,COLOR_GREEN,"Servico concluido! Pagamento recebido.");
        if(gJobVehicle[playerid] != INVALID_VEHICLE_ID)
        {
            DestroyVehicle(gJobVehicle[playerid]);
            gJobVehicle[playerid] = INVALID_VEHICLE_ID;
        }
    }
    return 1;
}

public OnPlayerText(playerid, text[])
{
    new name[MAX_PLAYER_NAME], msg[192];
    GetNYXPlayerName(playerid,name,sizeof(name));
    format(msg,sizeof(msg),"{FFFFFF}[PLAYER] %s: %s",name,text);
    SendClientMessageToAll(COLOR_WHITE,msg);
    return 0;
}

public OnDialogResponse(playerid, dialogid, response, listitem, inputtext[])
{
    if(dialogid == D_LOGIN)
    {
        if(response)
        {
            gLogged[playerid] = true;
            SendClientMessage(playerid,COLOR_GREEN,"Login aceito. Bem-vindo a NYX ROLEPLAY.");
            SpawnPlayer(playerid);
            return 1;
        }
        return ShowPlayerDialog(playerid,D_REGISTER,DIALOG_STYLE_PASSWORD,"NYX | REGISTRO","Crie sua senha.","CRIAR","VOLTAR");
    }
    if(dialogid == D_REGISTER)
    {
        if(!response) return ShowPlayerDialog(playerid,D_LOGIN,DIALOG_STYLE_PASSWORD,"NYX | LOGIN","Digite sua senha.","ENTRAR","REGISTRAR");
        gLogged[playerid] = true;
        gMoney[playerid] = 5000;
        gSkin[playerid] = 1;
        SendClientMessage(playerid,COLOR_GREEN,"Conta criada. Voce entrou como mendigo masculino.");
        SpawnPlayer(playerid);
        return 1;
    }
    if(dialogid == D_MAIN)
    {
        if(!response) return 1;
        if(listitem == 0) return SendClientMessage(playerid,COLOR_WHITE,"Use /status para ver seu personagem.");
        if(listitem == 1) return ShowPlayerDialog(playerid,D_JOBS,DIALOG_STYLE_LIST,"NYX | 15 EMPREGOS","Construcao / Obra\nMinerador\nFazendeiro\nFrentista\nGasista\nMotoboy\nCaminhoneiro\nTaxista\nMotorista de Onibus\nPescador\nEletricista\nEntregador\nColetor de Lixo\nMecanico\nAgricultor","ESCOLHER","FECHAR");
        if(listitem == 2) return ShowPlayerDialog(playerid,D_ORGS,DIALOG_STYLE_LIST,"NYX | ORGANIZACOES","Policia Militar\nPolicia Civil\nPolicia Federal\nBOPE\nForcas Armadas\nExercito Brasileiro\nMarinha do Brasil\nForca Aerea Brasileira\nGuarda Municipal\nSAMU\nCorpo de Bombeiros\nGoverno\nPrefeitura NYX\nJornal NYX\nMecanicos\nTransportes NYX\nBallas\nFamilies\nComando Vermelho\nPrimeiro Comando\nLos Aztecas\nCartel NYX\nMotoclube\nSindicato do Crime","ENTRAR","FECHAR");
        if(listitem == 3) return cmd_imoveis(playerid);
        if(listitem == 4) return cmd_leilao(playerid);
        if(listitem == 5) return ShowPlayerDialog(playerid,D_STORE,DIALOG_STYLE_LIST,"NYX | LOJA NCOINS","Skin premium 280 | 100 NCoins","COMPRAR","FECHAR");
        if(listitem == 6) return ShowPlayerDialog(playerid,D_SKIN,DIALOG_STYLE_INPUT,"NYX | SKINS","ID 0-311.\\n1 = mendigo masculino\\n2 = mendigo feminino","EQUIPAR","FECHAR");
        if(listitem == 7) return SendClientMessage(playerid,COLOR_NYX,"Familias: sistema de parentesco, membros, cargos e patrimonio compartilhado reservado para a camada persistente.");
        if(listitem == 8) return SendClientMessage(playerid,COLOR_NYX,"Casamento: /casar ID /aceitarcasamento /divorcio. Qualquer genero pode casar com qualquer genero.");
        if(listitem == 9) return cmd_admin(playerid);
    }
    if(dialogid == D_JOBS)
    {
        if(!response) return 1;
        gJob[playerid] = listitem;
        if(gJob[playerid] < 0 || gJob[playerid] >= 15) return 1;
        return SendClientMessage(playerid,COLOR_GREEN,"Emprego selecionado. Use /trabalhar.");
    }
    if(dialogid == D_ORGS)
    {
        if(!response) return 1;
        gOrg[playerid] = listitem;
        new msg[128];
        format(msg,sizeof(msg),"Organizacao selecionada: %s. Use /orgchat.",OrgName[gOrg[playerid]]);
        return SendClientMessage(playerid,COLOR_BLUE,msg);
    }
    if(dialogid == D_SKIN)
    {
        if(!response) return 1;
        new skin = strval(inputtext);
        if(skin < 0 || skin > 311) return SendClientMessage(playerid,COLOR_RED,"ID de skin invalido. Use 0-311.");
        gSkin[playerid] = skin;
        SetPlayerSkin(playerid,skin);
        return SendClientMessage(playerid,COLOR_GREEN,"Skin equipada.");
    }
    if(dialogid == D_PROPERTY)
    {
        if(!response || listitem < 0 || listitem >= MAX_PROPERTIES) return 1;
        if(PropertyOwner[listitem] != INVALID_PLAYER_ID) return SendClientMessage(playerid,COLOR_RED,"Este imovel ja possui dono.");
        if(gMoney[playerid] < PropertyPrice[listitem]) return SendClientMessage(playerid,COLOR_RED,"Dinheiro insuficiente.");
        gMoney[playerid] -= PropertyPrice[listitem];
        PropertyOwner[listitem] = playerid;
        gProperty[playerid] = listitem;
        return SendClientMessage(playerid,COLOR_GREEN,"Imovel comprado com sucesso.");
    }
    if(dialogid == D_AUCTION)
    {
        if(!response || listitem < 0 || listitem >= MAX_AUCTIONS) return 1;
        if(gMoney[playerid] <= AuctionBid[listitem]) return SendClientMessage(playerid,COLOR_RED,"Seu dinheiro nao cobre o lance atual.");
        AuctionBid[listitem] = gMoney[playerid];
        AuctionBidder[listitem] = playerid;
        return SendClientMessage(playerid,COLOR_GREEN,"Lance registrado.");
    }
    if(dialogid == D_STORE)
    {
        if(!response) return 1;
        if(gNCoins[playerid] < 100) return SendClientMessage(playerid,COLOR_RED,"Voce precisa de 100 NCoins.");
        gNCoins[playerid] -= 100;
        gSkin[playerid] = 280;
        SetPlayerSkin(playerid,280);
        return SendClientMessage(playerid,COLOR_GREEN,"Skin premium equipada por 100 NCoins.");
    }
    if(dialogid == D_ADMIN)
    {
        if(!response || gAdmin[playerid] < 1) return 1;
        if(listitem == 0)
        {
            gAdminInvisible[playerid] = !gAdminInvisible[playerid];
            HideAdminNameTag(playerid,gAdminInvisible[playerid]);
            return SendClientMessage(playerid,COLOR_GREEN,"Invisibilidade de administrador alterada. Nametag ocultada.");
        }
        if(listitem == 1) return SendAdminMessage("[ADMIN] Canal administrativo ativo.");
    }
    return 1;
}

stock cmd_imoveis(playerid)
{
    new list[2048],line[128];
    list[0] = EOS;
    for(new i=0;i<MAX_PROPERTIES;i++)
    {
        format(line,sizeof(line),"%d. %s | %s | $%d\\n",i+1,PropertyName[i],PropertyType[i],PropertyPrice[i]);
        strcat(list,line,sizeof(list));
    }
    return ShowPlayerDialog(playerid,D_PROPERTY,DIALOG_STYLE_LIST,"NYX | IMOVEIS",list,"COMPRAR","FECHAR");
}

stock cmd_leilao(playerid)
{
    new list[1536],line[128];
    list[0] = EOS;
    for(new i=0;i<MAX_AUCTIONS;i++)
    {
        format(line,sizeof(line),"Mansao %d | Lance $%d\\n",i+1,AuctionBid[i]);
        strcat(list,line,sizeof(list));
    }
    return ShowPlayerDialog(playerid,D_AUCTION,DIALOG_STYLE_LIST,"NYX | LEILAO DE MANSOES",list,"DAR LANCE","FECHAR");
}

stock cmd_admin(playerid)
{
    if(gAdmin[playerid] < 1) return SendClientMessage(playerid,COLOR_RED,"Acesso negado.");
    return ShowPlayerDialog(playerid,D_ADMIN,DIALOG_STYLE_LIST,"NYX | ADMIN","Alternar invisibilidade\nCanal administrativo","USAR","FECHAR");
}

public OnPlayerCommandText(playerid,cmdtext[])
{
    if(!gLogged[playerid]) return 1;
    if(!strcmp(cmdtext,"/menu",true)) return ShowPlayerDialog(playerid,D_MAIN,DIALOG_STYLE_LIST,"NYX ROLEPLAY | MENU","Meu personagem\nEmpregos\nOrganizacoes\nImoveis\nLeilao\nLoja NCoins\nSkins\nFamilias\nCasamento\nAdministracao","ABRIR","FECHAR");
    if(!strcmp(cmdtext,"/status",true))
    {
        new jobname[32],orgname[64],msg[256];
        if(gJob[playerid] >= 0) format(jobname,sizeof(jobname),"%s",JobName[gJob[playerid]]); else format(jobname,sizeof(jobname),"Desempregado");
        if(gOrg[playerid] >= 0) format(orgname,sizeof(orgname),"%s",OrgName[gOrg[playerid]]); else format(orgname,sizeof(orgname),"Civil");
        format(msg,sizeof(msg),"NYX | Dinheiro: $%d | NCoins: %d | Skin: %d | Emprego: %s | Org: %s",gMoney[playerid],gNCoins[playerid],gSkin[playerid],jobname,orgname);
        return SendClientMessage(playerid,COLOR_WHITE,msg);
    }
    if(!strcmp(cmdtext,"/empregos",true)) return ShowPlayerDialog(playerid,D_JOBS,DIALOG_STYLE_LIST,"NYX | 15 EMPREGOS","Construcao / Obra\nMinerador\nFazendeiro\nFrentista\nGasista\nMotoboy\nCaminhoneiro\nTaxista\nMotorista de Onibus\nPescador\nEletricista\nEntregador\nColetor de Lixo\nMecanico\nAgricultor","ESCOLHER","FECHAR");
    if(!strcmp(cmdtext,"/trabalhar",true))
    {
        if(gJob[playerid] < 0) return SendClientMessage(playerid,COLOR_YELLOW,"Escolha um emprego primeiro.");
        if(gJobCheckpoint[playerid]) return SendClientMessage(playerid,COLOR_YELLOW,"Voce ja esta trabalhando.");
        gJobCheckpoint[playerid] = 1;
        new city = random(5);
        SetPlayerCheckpoint(playerid,CityX[city],CityY[city],13.5,5.0);
        if(JobVehicleModel[gJob[playerid]] > 0) gJobVehicle[playerid] = CreateVehicle(JobVehicleModel[gJob[playerid]],1485.0,-1735.0,13.5,0.0,-1,-1,300);
        return SendClientMessage(playerid,COLOR_GREEN,"Servico iniciado. Siga o checkpoint.");
    }
    if(!strcmp(cmdtext,"/parartrabalho",true)){gJobCheckpoint[playerid]=0;DisablePlayerCheckpoint(playerid);if(gJobVehicle[playerid]!=INVALID_VEHICLE_ID){DestroyVehicle(gJobVehicle[playerid]);gJobVehicle[playerid]=INVALID_VEHICLE_ID;}return SendClientMessage(playerid,COLOR_YELLOW,"Trabalho encerrado.");}
    if(!strcmp(cmdtext,"/orgs",true)) return ShowPlayerDialog(playerid,D_ORGS,DIALOG_STYLE_LIST,"NYX | ORGANIZACOES","Policia Militar\nPolicia Civil\nPolicia Federal\nBOPE\nForcas Armadas\nExercito Brasileiro\nMarinha do Brasil\nForca Aerea Brasileira\nGuarda Municipal\nSAMU\nCorpo de Bombeiros\nGoverno\nPrefeitura NYX\nJornal NYX\nMecanicos\nTransportes NYX\nBallas\nFamilies\nComando Vermelho\nPrimeiro Comando\nLos Aztecas\nCartel NYX\nMotoclube\nSindicato do Crime","ENTRAR","FECHAR");
    if(!strcmp(cmdtext,"/orgchat",true))
    {
        new name[MAX_PLAYER_NAME],text[144];
        GetNYXPlayerName(playerid,name,sizeof(name));
        format(text,sizeof(text),"[ORG] %s: canal de organizacao ativo.",name);
        return SendOrgMessage(playerid,text);
    }
    if(!strcmp(cmdtext,"/skin",true)) return ShowPlayerDialog(playerid,D_SKIN,DIALOG_STYLE_INPUT,"NYX | SKINS","Digite um ID de 0 a 311.\n1 = mendigo masculino.\n2 = mendigo feminino.","EQUIPAR","FECHAR");
    if(!strcmp(cmdtext,"/imoveis",true)) return cmd_imoveis(playerid);
    if(!strcmp(cmdtext,"/vendercasa",true))
    {
        if(gProperty[playerid] < 0) return SendClientMessage(playerid,COLOR_RED,"Voce nao possui imovel.");
        new p = gProperty[playerid];
        PropertyOwner[p] = INVALID_PLAYER_ID;
        gProperty[playerid] = -1;
        GiveNYXMoney(playerid,PropertyPrice[p]/2);
        return SendClientMessage(playerid,COLOR_GREEN,"Imovel vendido por 50% do valor.");
    }
    if(!strcmp(cmdtext,"/leilao",true)) return cmd_leilao(playerid);
    if(!strcmp(cmdtext,"/pescar",true))
    {
        if(gFishing[playerid]) return SendClientMessage(playerid,COLOR_YELLOW,"Voce ja esta pescando.");
        gFishing[playerid] = 1;
        SetPlayerCheckpoint(playerid,1950.0,-1750.0,13.5,5.0);
        return SendClientMessage(playerid,COLOR_BLUE,"Pesca iniciada. Va ate o ponto de pesca.");
    }
    if(!strcmp(cmdtext,"/venderpeixe",true))
    {
        if(gFishCount[playerid] <= 0) return SendClientMessage(playerid,COLOR_RED,"Voce nao possui peixes.");
        new pay = gFishCount[playerid] * 350;
        gFishCount[playerid] = 0;
        return GiveNYXMoney(playerid,pay), SendClientMessage(playerid,COLOR_GREEN,"Peixes vendidos com sucesso.");
    }
    if(!strcmp(cmdtext,"/ncoins",true)){new msg[96];format(msg,sizeof(msg),"Seu saldo de NCoins: %d",gNCoins[playerid]);return SendClientMessage(playerid,COLOR_NYX,msg);}
    if(!strcmp(cmdtext,"/loja",true)) return ShowPlayerDialog(playerid,D_STORE,DIALOG_STYLE_LIST,"NYX | LOJA PREMIUM","Skin premium 280 | 100 NCoins","COMPRAR","FECHAR");
    if(!strcmp(cmdtext,"/casamento",true)) return SendClientMessage(playerid,COLOR_NYX,"/casar ID | /aceitarcasamento | /divorcio. Qualquer genero pode casar com qualquer genero.");
    if(!strncmp(cmdtext,"/casar ",7,true))
    {
        new target = strval(cmdtext[7]);
        if(!IsPlayerConnected(target) || target == playerid) return SendClientMessage(playerid,COLOR_RED,"Jogador invalido.");
        gPendingMarriage[target] = playerid;
        new name[MAX_PLAYER_NAME],msg[144];
        GetNYXPlayerName(playerid,name,sizeof(name));
        format(msg,sizeof(msg),"Voce recebeu uma proposta de casamento de %s. Use /aceitarcasamento.",name);
        SendClientMessage(target,COLOR_NYX,msg);
        return SendClientMessage(playerid,COLOR_GREEN,"Proposta enviada.");
    }
    if(!strcmp(cmdtext,"/aceitarcasamento",true))
    {
        new p = gPendingMarriage[playerid];
        if(p == INVALID_PLAYER_ID) return SendClientMessage(playerid,COLOR_RED,"Nenhuma proposta.");
        gMarriage[playerid] = p;
        gMarriage[p] = playerid;
        gPendingMarriage[playerid] = INVALID_PLAYER_ID;
        SendClientMessage(playerid,COLOR_GREEN,"Casamento realizado!");
        return SendClientMessage(p,COLOR_GREEN,"Casamento realizado!");
    }
    if(!strcmp(cmdtext,"/divorcio",true))
    {
        new p = gMarriage[playerid];
        if(p == INVALID_PLAYER_ID) return SendClientMessage(playerid,COLOR_RED,"Voce nao esta casado.");
        gMarriage[playerid] = INVALID_PLAYER_ID;
        if(IsPlayerConnected(p)) gMarriage[p] = INVALID_PLAYER_ID;
        return SendClientMessage(playerid,COLOR_GREEN,"Divorcio realizado.");
    }
    if(!strcmp(cmdtext,"/familias",true)) return SendClientMessage(playerid,COLOR_NYX,"Familias NYX: estrutura preparada para sobrenome, membros, cargos e patrimonio compartilhado.");
    if(!strcmp(cmdtext,"/admin",true)) return cmd_admin(playerid);
    if(!strcmp(cmdtext,"/achat",true)){if(gAdmin[playerid]<1)return 1;return SendAdminMessage("[ADMIN] Canal administrativo ativo.");}
    if(!strcmp(cmdtext,"/cidades",true)){new msg[256];format(msg,sizeof(msg),"NYX: %s | %s | %s | %s | %s",CityName[0],CityName[1],CityName[2],CityName[3],CityName[4]);return SendClientMessage(playerid,COLOR_NYX,msg);}
    if(!strcmp(cmdtext,"/ajuda",true)) return SendClientMessage(playerid,COLOR_WHITE,"/menu /status /empregos /trabalhar /orgs /orgchat /skin /imoveis /vendercasa /leilao /pescar /venderpeixe /ncoins /loja /casamento /familias /admin /cidades");
    return 0;
}

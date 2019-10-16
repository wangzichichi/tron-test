# 1 获取系统参数
**/api/system/status**  
Desc: List data synchronization state  
Demo: curl -X Get  https://apilist.tronscan.org/api/system/status  
@param: null;  
@return: data synchronization state;  
@TestOwner:王子赫;
@AutomationCase:true;


# 2 查询最新块
**/api/block/latest**  
Desc: Get the lastest block  
Demo: curl -X Get  https://apilist.tronscan.org/api/block/latest  
@param: null;  
@return: the latest block;  
@TestOwner:姜红;
@AutomationCase:true;

# 3 查询账户列表
**/api/account/list**  
Desc: List all the accounts in the blockchain (only 10,000 accounts are displayed, sorted by TRX balance from high to low) 
Demo: curl -X Get  https://apilist.tronscan.org/api/account/list?sort=-balance&limit=20&start=0  
@param sort: define the sequence of the records return;  
@param limit: page size for pagination;  
@param start: query index for pagination;  
@return: accounts list;  
@TestOwner:朱凯;
@AutomationCase:false;

# 4 查询具体地址
**/api/account**  
Desc: Get a single account's detail  
Demo: curl -X Get  https://apilist.tronscan.org/api/account?address=TWd4WrZ9wn84f5x1hZhL4DHvk738ns5jwb  
@param address: an account;  
@return: an account detail info;  
@TestOwner:姜红;
@AutomationCase: true;  

# 5 查询区块列表
**/api/block**  
Desc: List the blocks in the blockchain(only display the latest 10,000 data records in the query time range)  
Demo: curl -X Get  https://apilist.tronscan.org/api/block?sort=-number&limit=20&count=true&start=20&start_timestamp=1551715200000&end_timestamp=1551772172616 
@param sort: define the sequence of the records return;  
@param limit: page size for pagination;  
@param start: query index for pagination;  
@param count: total number of records;  
@param start_timestamp: query date range;  
@param end_timestamp: query date range;  
@return: blocks list;  
@TestOwner:朱凯;
@AutomationCase:false;

# 6 查询具体区块producer
**/api/block**  
Desc: List all the blocks produced by the specified SR in the blockchain  
Demo: curl -X Get  https://apilist.tronscan.org/api/block?sort=-number&limit=20&count=true&start=0&producer=TMuA6YqfCeX8EhbfYEg5y7S4DqzSJireY9  
@param sort: define the sequence of the records return;  
@param limit: page size for pagination;  
@param start: query index for pagination;  
@param count: total number of records;  
@param start_timestamp: query date range;  
@param end_timestamp: query date range;  
@param producer: SR address;  
@return: blocks produced by the specified SR;  
@TestOwner:姜红;
@AutomationCase:true;

# 7 查询具体区块
**/api/block**  
Desc: Get a single block's detail  
Demo: curl -X Get  https://apilist.tronscan.org/api/block?number=5987471  
@param number: block number;  
@return: a block detail info;  
@TestOwner:朱凯;
@AutomationCase:false;

# 8 查询交易列表
**/api/transaction**  
Desc: List the transactions in the blockchain(only display the latest 10,000 data records in the query time range)  
Demo: curl -X Get  https://apilist.tronscan.org/api/transaction?sort=-timestamp&count=true&limit=20&start=0&start_timestamp=1548000000000&end_timestamp=1548056638507  
@param sort: define the sequence of the records return;  
@param limit: page size for pagination;  
@param start: query index for pagination;  
@param count: total number of records;  
@param start_timestamp: query date range;  
@param end_timestamp: query date range;  
@return: transactions list;  
@TestOwner:姜红;
@AutomationCase:true;

# 9 地址下交易查询
**/api/transaction**  
Desc: List the transactions related to a specified account  
Demo: curl -X Get  https://apilist.tronscan.org/api/transaction?sort=-timestamp&count=true&limit=20&start=0&address=TMuA6YqfCeX8EhbfYEg5y7S4DqzSJireY9  
@param sort: define the sequence of the records return;  
@param limit: page size for pagination;  
@param start: query index for pagination;  
@param count: total number of records;  
@param start_timestamp: query date range;  
@param end_timestamp: query date range;  
@param address: an account;  
@return: transactions list;
@TestOwner:朱凯;
@AutomationCase:false;

# 10 查询合约下交易
**/api/contracts/transaction**(only display the latest 10,000 data records in the query time range)  
Desc:  List the transactions related to an smart contract(only display the latest 10,000 data records in the query time range)     
Demo: curl -X Get  https://apilist.tronscan.org/api/contracts/transaction?sort=-timestamp&count=true&limit=20&start=0&contract=TWfbypo79cDaXidFBdkDNuVJNAXzPdTmcx  
@param sort: define the sequence of the records return;  
@param limit: page size for pagination;  
@param start: query index for pagination;  
@param count: total number of records;  
@param start_timestamp: query date range;  
@param end_timestamp: query date range;  
@param contract: contract address;  
@return: transactions list;  
@TestOwner:王子赫;
@AutomationCase:true;

# 11 查询具体交易
**/api/transaction-info**  
Desc:  List a transaction detail  
Demo: curl -X Get  https://apilist.tronscan.org/api/transaction-info?hash=1c25bc75d0bebac2f3aa71c350d67c4eed56ec2501b72302ae6d0110dc40cf96  
@param hash: query transaction hash;  
@return: a transaction detail info;  
@TestOwner:王子赫;
@AutomationCase:true;

# 12 查询转账列表
**/api/transfer**  
Desc:  List the transfers in the blockchain(only display the latest 10,000 data records in the query time range)  
Demo: curl -X Get  https://apilist.tronscan.org/api/transfer?sort=-timestamp&count=true&limit=20&start=0&start_timestamp=1548000000000&end_timestamp=1548057645667  
@param sort: define the sequence of the records return;  
@param limit: page size for pagination;  
@param start: query index for pagination;  
@param count: total number of records;  
@param start_timestamp: query date range;  
@param end_timestamp: query date range;  
@return: all the transfers list;  
@TestOwner:王子赫;
@AutomationCase:true;

# 13
**/api/transfer**  
Desc: List the transfers related to an specified account(only display the latest 10,000 data records in the query time range)
Demo: curl -X Get  https://apilist.tronscan.org/api/transfer?sort=-timestamp&count=true&limit=20&start=0&token=_&address=TMuA6YqfCeX8EhbfYEg5y7S4DqzSJireY9  
@param sort: define the sequence of the records return;  
@param limit: page size for pagination;  
@param start: query index for pagination;  
@param count: total number of records;  
@param start_timestamp: query date range;  
@param end_timestamp: query date range;  
@param token: '_' shows only TRX transfers;  
@param address: transfers related address;  
@return: transfers list related to an specified account;  
@TestOwner:朱凯;
@AutomationCase:false;

# 14 查询节点
**/api/nodemap**  
Desc: List all the nodes in the blockchain  
Demo: curl -X Get  https://apilist.tronscan.org/api/nodemap  
@param: null;  
@return: all the nodes;  
@TestOwner:姜红;
@AutomationCase:true;

# 15 获取捐赠者信息
**/api/listdonators**  
Desc: List all the donators to the foundation address  
Demo: curl -X Get  https://apilist.tronscan.org/api/listdonators  
@param: null;  
@return: all the donators;  
@TestOwner:王子赫;
@AutomationCase:true;

# 16 查询fund信息
**/api/fund**  
Desc: List all the foundation addresses  
Demo: curl -X Get  https://apilist.tronscan.org/api/fund?page_index=1&per_page=20  
@param page_index: query index for pagination;  
@param per_page: page size for pagination;  
@return: all the foundation addresses;  
@TestOwner:姜红;
@AutomationCase:true;

# 17 查询捐赠总览信息
**/api/funds**  
Desc: List TRX number overview info  
Demo: curl -X Get  https://apilist.tronscan.org/api/funds  
@param: null;  
@return: TRX number overview info;  
@TestOwner:朱凯;
@AutomationCase:false;

# 18 查询合约列表
**/api/contracts**  
Desc: List all the contracts in the blockchain  
Demo: curl -X Get  https://apilist.tronscan.org/api/contracts?count=true&limit=20&start=0  
@param limit: page size for pagination;  
@param start: query index for pagination;  
@param count: total number of records;  
@return: contracts list;  
@TestOwner:王子赫;
@AutomationCase:true;

# 19 查询合约
**/api/contract**  
Desc: Get a single contract's detail  
Demo: curl -X Get  https://apilist.tronscan.org/api/contract?contract=TEEXEWrkMFKapSMJ6mErg39ELFKDqEs6w3  
@param contract: contract address;  
@return: a contract detail info;  
@TestOwner:姜红;
@AutomationCase:true;

# 20 获取合约编码
**/api/contracts/code**  
Desc: Get a single contract's abi & byteCode  
Demo: curl -X Get  https://apilist.tronscan.org/api/contracts/code?contract=TEEXEWrkMFKapSMJ6mErg39ELFKDqEs6w3  
@param contract: contract address;  
@return: a single contract's abi & byteCode;  
@TestOwner:朱凯;
@AutomationCase:false;

# 21 查询合约调用
**/api/contracts/trigger**  
Desc: List all the triggers of the contracts in the blockchain(only display the latest 10,000 data records in the query time range)  
Demo: curl -X Get  https://apilist.tronscan.org/api/contracts/trigger?sort=-timestamp&count=true&limit=20&start=0&start_timestamp=1548000000000&end_timestamp=1548060167540  
@param sort: define the sequence of the records return;  
@param limit: page size for pagination;  
@param start: query index for pagination;  
@param count: total number of records;  
@param start_timestamp: query date range;  
@param end_timestamp: query date range;  
@return: a single contract's trigger detail;  
@TestOwner:朱凯;
@AutomationCase:true;

# 22 查询trc20
**/api/token_trc20**  
Desc: List all the trc20 tokens in the blockchain  
Demo: curl -X Get  https://apilist.tronscan.org/api/token_trc20?limit=20&start=0  
@param limit: page size for pagination;  
@param start: query index for pagination;  
@return: trc20 tokens list;  
@TestOwner:姜红;
@AutomationCase:true;

# 23 获取token列表信息
**/api/token**  
Desc: List all the trc10 tokens in the blockchain  
Demo: curl -X Get  https://apilist.tronscan.org/api/token?sort=-name&limit=20&start=0&totalAll=1&status=ico  
@param sort: define the sequence of the records return;  
@param limit: page size for pagination;  
@param start: query index for pagination;  
@param totalAll: the total number of the trc10 tokens(audited and Unaudited tokens both included);  
@param status: if equals 'ico', only returns the tokens that can be participated;  
@return: trc10 tokens list(only audited tokens);  
@TestOwner:朱凯;
@AutomationCase:true;

# 24 获取token列表信息
**/api/token**  
Desc: List a single trc10 token's detail  
Demo: curl -X Get  https://apilist.tronscan.org/api/token?id=1001761&showAll=1  
@param id: token id;  
@param showAll: if equals 1, audited and Unaudited tokens can both be returned;  
@return: a single trc10 token's detail;  
@TestOwner:王子赫;
@AutomationCase:true;

# 25 获取witness
**/api/witness**  
Desc: List all the witnesses in the blockchain  
Demo: curl -X Get  https://apilist.tronscan.org/api/witness  
@param: null;  
@return: all the witnesses in the blockchain;  
@TestOwner:朱凯;
@AutomationCase:true;



# 26 witness查询
**/api/vote/witness**  
Desc: List all the votes info of the witnesses  
Demo: curl -X Get  https://apilist.tronscan.org/api/vote/witness  
@param: null;  
@return: all the votes info of the witnesses;  
@TestOwner:王子赫;
@AutomationCase:true;

# 27 地址下投票查询
**/api/vote**  
Desc: List all the votes info made by a specified voter  
Demo: curl -X Get  https://apilist.tronscan.org/api/vote?sort=-votes&limit=20&start=0&voter=TMuA6YqfCeX8EhbfYEg5y7S4DqzSJireY9  
@param sort: define the sequence of the records return;  
@param limit: page size for pagination;  
@param start: query index for pagination;  
@param voter: an voter account;  
@return: all the votes info made by a specified voter;  
@TestOwner:姜红;
@AutomationCase:true;

# 28 candidate下投票查询
**/api/vote**  
Desc: List all the voters that vote for a specified candidate  
Demo: curl -X Get  https://apilist.tronscan.org/api/vote?sort=-votes&limit=20&start=0&candidate=TGzz8gjYiYRqpfmDwnLxfgPuLVNmpCswVp  
@param sort: define the sequence of the records return;  
@param limit: page size for pagination;  
@param start: query index for pagination;  
@param candidate: an witness account;  
@return: all the voters that vote for a specified candidate; 
@TestOwner:朱凯;
@AutomationCase:true; 

# 29 获取链上参数
**/api/chainparameters**  
Desc: List all the proposal parameters  
Demo: curl -X Get  https://apilist.tronscan.org/api/chainparameters  
@param: null;  
@return: all the proposal parameters;  
@TestOwner:王子赫;
@AutomationCase:true;

# 30 获取提案列表
**/api/proposal**  
Desc: List all the proposals in the blockchain  
Demo: curl -X Get  https://apilist.tronscan.org/api/proposal?sort=-number&limit=20&start=0  
@param sort: define the sequence of the records return;  
@param limit: page size for pagination;  
@param start: query index for pagination;  
@return: all the proposals;  
@TestOwner:姜红;
@AutomationCase:true;

# 31 获取提案
**/api/proposal**  
Desc: List a single proposal detail  
Demo: curl -X Get  https://apilist.tronscan.org/api/proposal?id=16  
@param id: proposal id;  
@return: a single proposal detail info;  
@TestOwner:朱凯;
@AutomationCase:true;

# 32 列出exchange信息
**/api/exchanges/list**  
Desc: List all the audited exchange pairs  
Demo: curl -X Get  https://apilist.tronscan.org/api/exchanges/list?sort=-balance  
@param sort: define the sequence of the records return;  
@return: all the audited exchange pairs;  
@TestOwner:王子赫;
@AutomationCase:true;

# 33 列出所有交易所信息
**/api/exchanges/listall**  
Desc: List all the exchange pairs in the blockchain  
Demo: curl -X Get  https://apilist.tronscan.org/api/exchanges/listall  
@param: null;  
@return: all the exchange pairs;  
@TestOwner:王子赫;
@AutomationCase:true;

# 34 查询交易所信息
**/api/exchange/transaction**  
Desc: List a single the exchange pair's transaction records  
Demo: curl -X Get  https://apilist.tronscan.org/api/exchange/transaction?sort=-timestamp&start=0&limit=15&exchangeID=9  
@param sort: define the sequence of the records return;  
@param limit: page size for pagination;  
@param start: query index for pagination;  
@param exchangeID: exchange id;  
@return: a single the exchange pair's transaction records;  
@TestOwner:姜红;
@AutomationCase:true;

# 35 获取交易所k线信息
**/api/exchange/kgraph**  
Desc: List a single the exchange pair's trade chart data  
Demo: curl -X Get  https://apilist.tronscan.org/api/exchange/kgraph?exchange_id=9&granularity=1h&time_start=1547510400&time_end=1548062933  
@param start_timestamp: query date range;  
@param end_timestamp: query date range;  
@param granularity: data statistics time interval;  
@param exchange_id: exchange id;  
@return: a single the exchange pair's trade chart data;  
@TestOwner:朱凯;
@AutomationCase:true;

# 36 获取交易所k线信息
**/api/exchange/kgraph**  
Desc: List a single the exchange pair's trade chart data  
Demo: curl -X Get  https://apilist.tronscan.org/api/exchange/kgraph?exchange_id=9&granularity=1h&time_start=1547510400&time_end=1548062933  
@param start_timestamp: query date range;  
@param end_timestamp: query date range;  
@param granularity: data statistics time interval;  
@param exchange_id: exchange id;  
@return: a single the exchange pair's trade chart data;  
@TestOwner:姜红;
@AutomationCase:true;

# 37 全局统计接口
**/api/stats/overview**  
Desc: Blockchain data overview in history  
Demo: curl -X Get  https://apilist.tronscan.org/api/stats/overview  
@param: null;  
@return: list of avgBlockTime, blockchainSize, newAddressSeen, newBlockSeen, newTransactionSeen, totalAddress, totalBlockCount, totalTransaction;  
@TestOwner:姜红;
@AutomationCase:true;

# 38
**/api/broadcast**  
Desc: Broadcast a transaction to the blockchain  
Demo: curl -X Post  https://apilist.tronscan.org/api/broadcast  
@param: transaction: signature generated hex;  
@return: broadcast result;  
@TestOwner:王子赫;
@AutomationCase:true;

# 39 获取合约事件
**/api/contract/events**   
Desc: List the TRC-20 transfers related to a specified account(only display the latest 10,000 data records in the query time range)   
Demo: curl -X Get  https://apilist.tronscan.org/api/contract/events?address=TSbJFbH8sSayRFMavwohY2P6QfKwQEWcaz&start=0&limit=20&start_timestamp=1548000000000&end_timestamp=1548056638507   
@param limit: page size for pagination;   
@param start: query index for pagination;   
@param start_timestamp: query date range;   
@param end_timestamp: query date range;   
@param address: an account;   
@return: TRC-20 transfers list; 
@TestOwner:王子赫;
@AutomationCase:true;  

# 40 账户下合约内交易
**/api/internal-transaction**   
Desc: List the internal transactions related to a specified account(only display the latest 10,000 data records in the query time range)    
Demo: curl -X Get  https://apilist.tronscan.org/api/internal-transaction?limit=20&start=0&address=TBTzh1N24TUinHHrnxZoAv7ouWrNe6M9n2&start_timestamp=1529856000000&end_timestamp=1552549684954    
@param limit: page size for pagination;   
@param start: query index for pagination;    
@param start_timestamp: query date range;    
@param end_timestamp: query date range;    
@param address: an account;    
@return: internal transactions list;    
@TestOwner:姜红;
@AutomationCase:true;

# 41 查询trc10通证下交易
**/api/asset/transfer**   
Desc: List the transfers related to a specified TRC10 token(only display the latest 10,000 data records in the query time range)   
Demo: curl -X Get  https://apilist.tronscan.org/api/asset/transfer?limit=20&start=0&name=IGG&issueAddress=TSbhZijH2t7Qn1UAHAu7PBHQdVAvRwSyYr&start_timestamp=1529856000000&end_timestamp=1552549912537   
@param limit: page size for pagination;   
@param start: query index for pagination;   
@param name: token name;   
@param issueAddress: token creation address;   
@param start_timestamp: query date range;   
@param end_timestamp: query date range;   
@return: TRC10 token transfers list;
@TestOwner:朱凯;
@AutomationCase:true;   

# 42 查询trc20通证转账
**/api/token_trc20/transfers**   
Desc: List the transfers related to a specified TRC20 token(only display the latest 10,000 data records in the query time range)   
Demo: curl -X Get  https://apilist.tronscan.org/api/token_trc20/transfers?limit=20&start=0&contract_address=TCN77KWWyUyi2A4Cu7vrh5dnmRyvUuME1E&start_timestamp=1529856000000&end_timestamp=1552550375474   
@param limit: page size for pagination;   
@param start: query index for pagination;   
@param contract_address: contract address;   
@param start_timestamp: query date range;   
@param end_timestamp: query date range;   
@return: TRC20 token transfers list;   
@TestOwner:朱凯;  
@AutomationCase:true;

# 43 提供CNY货币转换价格
**/api/system/proxy**   
Desc: add CNY price   
Demo: curl -X Get  https://apilist.tronscan.org/api/system/proxy?url=https://api.coinmarketcap.com/v1/ticker/tronix/?convert=CNY   
@param url: Plug-in address;      
@return: proxy token transfers list;   
@TestOwner:姜红;  
@AutomationCase:true;

# 44 查询通证列表
**/api/tokens/overview**  
Desc: List all the tokens in the blockchain (including trc10 and trc20 tokens)    
Demo: curl -X Get  https://apilist.tronscan.org/api/tokens/overview?start=0&limit=20&order=desc&filter=all&sort=volume24hInTrx&order_current=descend  
@param limit: page size for pagination;  
@param start: query index for pagination;  
@param order: define the sequence order of the records return;   
@param start_timestamp: query date range;  
@param end_timestamp: query date range;  
@param sort: define the sorting rule;  
@param filter: define the tokens return. "trc10" for trc10 tokens, "trc20" for trc20 tokens; "all" for all trc10 and trc20 tokens
@return: tokens list;  
@TestOwner:姜红;  
@AutomationCase:true;

# 45 查询区块上交易列表
**/api/transaction**  
Desc: 查询区块上交易列表  
Demo: curl -X Get  https://apilist.tronscan.org/api/transaction?sort=-timestamp&count=true&limit=20&start=0&total=0&block=12448572  
@param sort: define the sequence of the records return;  
@param limit: page size for pagination;  
@param count: total number of records;  
@param start: query date range;  
@param total: total number;   
@param block: block number;  
@return: proxy token transfers list;     
@TestOwner:姜红;  
@AutomationCase:true;

# 46 查询账户交易统计信息
**/api/account/stats**  
Desc: 查询账户交易统计信息  
Demo: curl -X Get  https://apilist.tronscan.org/api/account/stats?address=TMuA6YqfCeX8EhbfYEg5y7S4DqzSJireY9
@param address: token creation address;   
@return: proxy token transfers list;     
@TestOwner:姜红;    
@AutomationCase:true;

# 47 地址的witness查询
**/api/vote/witness**  
Desc: 地址的witness查询  
Demo: curl -X Get  https://apilist.tronscan.org/api/vote/witness?address=TMuA6YqfCeX8EhbfYEg5y7S4DqzSJireY9  
@param address: token creation address;   
@return: proxy token transfers list;     
@TestOwner:姜红;  
@AutomationCase:true;

# 48 地址下的转账查询
**/api/trc10trc20-transfer**  
Desc: List the transfers related to specified account(only display the latest 2,000 data records in the query time range)
Demo: curl -X Get  https://apilist.tronscan.org/api/trc10trc20-transfer?sort=-timestamp&limit=20&start=0&total=0&start_timestamp=1568972315916&end_timestamp=1569577115916&direction=all&address=TMuA6YqfCeX8EhbfYEg5y7S4DqzSJireY9  
@param sort: define the sequence of the records return;  
@param limit: page size for pagination;  
@param start: query index for pagination;  
@param asset_name: transfer asset name;  
@param start_timestamp: query date range;  
@param end_timestamp: query date range;  
@param direction: all for all transfers; in for transfer-in; out for transfer-out  
@return: all the transfers list;  
@TestOwner:姜红;  
@AutomationCase:true;

# 49 查询合约内部合约内交易
**/api/internal-transaction**   
Desc: List the internal transactions related to a specified contract (only display the latest 2,000 data records in the query time range)    
Demo: curl -X Get  https://apilist.tronscan.org/api/internal-transaction?limit=20&start=0&contract=TWmhXhXjgXTj87trBufmWFuXtQP8sCWZZV&start_timestamp=1529856000000&end_timestamp=1552549684954    
@param limit: page size for pagination;   
@param start: query index for pagination;    
@param start_timestamp: query date range;    
@param end_timestamp: query date range;    
@param contract: contract address;    
@return: contract internal transactions list;    
@TestOwner:姜红;
@AutomationCase:true;

# 50 查询trc20通证持有者
**/api/token_trc20/holders**  
Desc: Get token holders of a trc20 token;    
Demo: curl -X Get  https://apilist.tronscan.org/api/token_trc20/holders?sort=-balance&start=0&limit=20&contract_address=TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t  
@param sort: define the sequence of the records return;   
@param limit: page size for pagination;    
@param start: query index for pagination;     
@param contract_address: trc20 token address;  
@return: token holders of a trc20 token;  
@TestOwner:姜红;  
@AutomationCase:true;

# 51 声明接口
**/api/announcement**  
Desc: get all announcements of tronscan and trxmarket   
Demo: curl -X Get  https://apilist.tronscan.org/api/announcement?start=10&limit=10&type=1&status=0&sort=-timestamp  
@param limit: page size for pagination;    
@param start: query index for pagination;  
@param type: announcement type, now all equal 1;  
@param status: announcement status, 0 or 1;  
@param sort: define the sequence of the records return;   
@return: list of the detailed announcement;   
@TestOwner:姜红;  
@AutomationCase:true;

# 52 上传节点信息
**/api/v2/node/info_upload**  
Desc: download the transaction info of a address (recent 10,000 transactions)  
Demo: curl -X Get  https://apilist.tronscan.org/api/v2/node/info_upload?address=TMuA6YqfCeX8EhbfYEg5y7S4DqzSJireY9  
@param address: the address  
@return: csv file of the address' transaction;   
@TestOwner:姜红;  
@AutomationCase:true;

# 53
**/api/v2/node/overview_upload**   
Desc: download daily new address number and transaction number info  
Demo: curl -X Get  https://apilist.tronscan.org/api/v2/node/overview_upload  
@param: null  
@return: csv file of daily new address number and transaction number info;  
@TestOwner:姜红;  
@AutomationCase:true;

# 54 获取Bittorrent的流通量和市值
**/api/bittorrent/fund**  
Desc: 获取Bittorrent的流通量和市值  
Demo: curl -X Get  https://apilist.tronscan.org/api/bittorrent/fund   
@param: null;    
@return: bittorrent的流通量和市值;  
@TestOwner:姜红;  
@AutomationCase:true;

# 55 获取bittorrent代笔解锁时间表图
**/api/bittorrent/graphic**  
Desc: 获取bittorrent代笔解锁时间表图      
Demo: curl -X Get  https://apilist.tronscan.org/api/bittorrent/graphic    
@param: null;    
@return: bittorrent代笔解锁时间表图;   
@TestOwner:姜红;  
@AutomationCase:true;

# 56 获取wink的流通量和市值
**/api/wink/fund**  
Desc: 获取wink的流通量和市值  
Demo: curl -X Get  https://apilist.tronscan.org/api/wink/fund    
@param: null;    
@return: wink的流通量和市值;   
@TestOwner:姜红;  
@AutomationCase:true;

# 57 搜索框查询接口--
**/api/search**  
Desc: 搜索框查询接口   
Demo: curl -X Get  https://apilist.tronscan.org/api/search?term=888       
@param: null;    
@return: 搜索框查询接口;   
@TestOwner:姜红;  
@AutomationCase:true;

# 58 查询投票接口
**/api/account/votes**  
Desc: Get specific account's vote list;     
Demo: curl -X Get  https://apilist.tronscan.org/api/account/votes?address=TAUVtaa3wX2omBP683qtPLjRHVQBsDXYS3  
@param address: an account;    
@return: specific account's vote list;  
@TestOwner:姜红;  
@AutomationCase:true;

# 59 查看SR信息
**/api/account/sr**  
Desc: Get a super representative's github link;   
Demo: curl -X Get  https://apilist.tronscan.org/api/account/sr?address=TGzz8gjYiYRqpfmDwnLxfgPuLVNmpCswVp  
@param address: super representative address;     
@return: super representative's github link;         
@TestOwner:姜红;  
@AutomationCase:true;

# 60 查询总数接口
**/api/count**  
Desc: Get a super totalCount's github link;   
Demo: curl -X Get  http://apilist.tronscan.org/api/count?type=trc10trc20&address=TAahLbGTZk6YuCycii72datPQEtyC5x231  
@param address: super representative address;    
@param type:  announcement type, now all equal 1;    
@return: super representative's github link;  
@TestOwner:姜红;  
@AutomationCase:true;

# 61 获取当前投票信息
**/api/vote/current-cycle**  
Desc: 获取当前投票信息     
Demo: curl -X Get https://apilist.tronscan.org/api/vote/current-cycle  
@param: null;    
@return: 当前投票信息;   
@TestOwner:姜红;  
@AutomationCase:true;

# 62 获取下一轮投票情况
**/api/vote/next-cycle**  
Desc: Get next round vote time;  
Demo: curl -X Get https://apilist.tronscan.org/api/vote/next-cycle    
@param: null;    
@return: next round vote time;  
@TestOwner:姜红;  
@AutomationCase:true; 

# 63 获取maintenance的witness方法
**/api/witness/maintenance-statistic**  
Desc: 获取本轮出块算力分布图  
Demo: curl -X Get  https://apilist.tronscan.org/api/witness/maintenance-statistic  
@param: null;  
@return: 本轮算力分布图;  
@TestOwner:姜红;  
@AutomationCase:true; 

# 64 根据tokenName获取token的信息
**/api/token/address**
 Desc: Get token holders balance by token name (Deprecated)    
 Demo: curl -X Get  https://apilist.tronscan.org/api/token/address?tokenName=USDT&sort=-balance&start=0@limit=20
 @param tokenName: token name;   
 @param sort: define the sequence of the records return;  
 @param limit: page size for pagination;   
 @param start: query index for pagination;  
 @return: token holders and balance;  
 @TestOwner:姜红;  
 @AutomationCase:true;

# 65 获取simple-transfer方法
**/api/simple-transfer**  
 Desc: List the transfers under specified condition  
 Demo: curl -X Get  https://apilist.tronscan.org/api/simple-transfer?start_timestamp=1548000000000&end_timestamp=1548056638507&from=TXYeahu7J6Hr7X33XFRaHgyznvun578jPm&to=THzuXNFiDe4jBGiVRpRLxCf4u3WWxgrUZE&asset_name=trx&sort=-timestamp   
 @param sort: define the sequence of the records return;  
 @param to: to address;  
 @param from: from address;  
 @param asset_name: transfer asset name;  
 @param start_timestamp: query date range;  
 @param end_timestamp: query date range;  
 @return: all the transfers list;
 @TestOwner:姜红;
 @AutomationCase:true; 
 
# 66 获取trc10 token持有者
**/api/tokenholders**  
 Desc: Get token holders of a trc10 token;   
 Demo: curl -X Get  https://apilist.tronscan.org/api/tokenholders?sort=-balance&limit=20&start=0&address=TF5Bn4cJCT6GVeUgyCN4rBhDg42KBrpAjg  
 @param id: token id;  
 @param sort: define the sequence of the records return;   
 @param limit: page size for pagination;  
 @param start: query index for pagination;   
 @param address: trc10 token address;  
 @return: a single trc10 token's detail;  
 @TestOwner:姜红;  
 @AutomationCase:true; 
 
# 67 获取系统tps信息
**/api/system/tps**  
 Desc: List data synchronization tps  
 Demo: curl -X Get  https://apilist.tronscan.org/api/system/tps  
 @param: null;  
 @return: data synchronization state;    
 @TestOwner:姜红;   
 @AutomationCase:true; 
 
# 68 获取homepage相关信息
**/api/system/homepage-bundle**  
 Desc: List data synchronization homepage  
 Demo: curl -X Get  http://apilist.tronscan.org/api/system/homepage-bundle  
 @param: null;  
 @return: data synchronization state;    
 @TestOwner:姜红;   
 @AutomationCase:true; 
 
# 69 能量统计接口
 **/api/energystatistic**  
  Desc: List data synchronization energystatistic  
  Demo: curl -X Get  http://apilist.tronscan.org/api/energystatistic  
  @param: null;  
  @return: data synchronization energystatistic;    
  @TestOwner:姜红;   
  @AutomationCase:true; 
  
# 70 获取能量每日统计信息
 **/api/energydailystatistic**  
  Desc: List data synchronization energydailystatistic  
  Demo: curl -X Get  http://apilist.tronscan.org/api/energydailystatistic  
  @param: null;  
  @return: data synchronization energydailystatistic;    
  @TestOwner:姜红;   
  @AutomationCase:true; 
  
# 71 trigger统计
 **/api/triggerstatistic**  
  Desc: List data synchronization triggerstatistic  
  Demo: curl -X Get  http://apilist.tronscan.org/api/triggerstatistic   
  @param: null;  
  @return: data synchronization triggerstatistic;    
  @TestOwner:姜红;   
  @AutomationCase:true;
  
# 72 trigger总数统计
 **/api/triggeramountstatistic**  
  Desc: List data synchronization triggeramountstatistic  
  Demo: curl -X Get  http://apilist.tronscan.org/api/triggeramountstatistic   
  @param: null;  
  @return: data synchronization triggeramountstatistic;    
  @TestOwner:姜红;   
  @AutomationCase:true;
  
# 73 合约调用者信息统计   
 **/api/calleraddressstatistic**  
  Desc: List data synchronization calleraddressstatistic  
  Demo: curl -X Get  http://apilist.tronscan.org/api/calleraddressstatistic   
  @param: null;  
  @return: data synchronization calleraddressstatistic;    
  @TestOwner:姜红;   
  @AutomationCase:true;
  
# 74 合约调用者数量统计信息   
 **/api/calleraddressamountstatistic**  
  Desc: List data synchronization calleraddressamountstatistic  
  Demo: curl -X Get  http://apilist.tronscan.org/api/calleraddressamountstatistic   
  @param: null;  
  @return: data synchronization calleraddressamountstatistic;    
  @TestOwner:姜红;   
  @AutomationCase:true;
  
# 75 合约能量统计信息   
 **/api/onecontractenergystatistic**  
  Desc: List data synchronization onecontractenergystatistic  
  Demo: curl -X Get  http://apilist.tronscan.org/api/onecontractenergystatistic?address=TEEXEWrkMFKapSMJ6mErg39ELFKDqEs6w3   
  @param: null;  
  @return: data synchronization onecontractenergystatistic;    
  @TestOwner:姜红;   
  @AutomationCase:true;
    
# 76 单个合约调用统计信息   
 **/api/onecontracttriggerstatistic**  
  Desc: List data synchronization onecontracttriggerstatistic  
  Demo: curl -X Get  http://apilist.tronscan.org/api/onecontracttriggerstatistic?address=TEEXEWrkMFKapSMJ6mErg39ELFKDqEs6w3   
  @param: null;  
  @return: data synchronization onecontracttriggerstatistic;    
  @TestOwner:姜红;   
  @AutomationCase:true;
      
# 77 单个合约调用者统计信息   
 **/api/onecontractcallerstatistic**  
  Desc: List data synchronization onecontractcallerstatistic  
  Demo: curl -X Get  http://apilist.tronscan.org/api/onecontractcallerstatistic?address=TEEXEWrkMFKapSMJ6mErg39ELFKDqEs6w3   
  @param: null;  
  @return: data synchronization onecontractcallerstatistic;    
  @TestOwner:姜红;   
  @AutomationCase:true;
  
# 78 一个合约的caller信息   
 **/api/onecontractcallers**  
  Desc: List data synchronization onecontractcallers  
  Demo: curl -X Get  http://apilist.tronscan.org/api/onecontractcallers?address=TEEXEWrkMFKapSMJ6mErg39ELFKDqEs6w3   
  @param: null;  
  @return: data synchronization onecontractcallers;    
  @TestOwner:姜红;   
  @AutomationCase:true;
    
# 79 一个合约的callvalue统计信息   
 **/api/onecontract-callvalue**  
  Desc: List data synchronization onecontract-callvalue  
  Demo: curl -X Get  http://apilist.tronscan.org/api/onecontract-callvalue?address=TEEXEWrkMFKapSMJ6mErg39ELFKDqEs6w3   
  @param: null;  
  @return: data synchronization onecontract-callvalue;    
  @TestOwner:姜红;   
  @AutomationCase:true;
fun main() {
    val sample1 = generateTree(
        arrayOf(
            "31,Site de investimentos,,",
            "33,Notícias,,31",
            "47,Nacionais,/noticias-nacionais,33",
            "49,Internacionais,/noticias-internacionais,33",
            "53,Economia,,31",
            "57,Bolsa de valores,,53",
            "61,Ações,/acoes,57",
            "65,Fundos imobiliários,/fii,57",
            "72,Indicadores,/indicadores,53",
            "75,Blog,/blog,53"
        )
    )
    sample1.printTree()
    println()
    println()
    val sample2 = generateTree(
        arrayOf(
            "722,Sistema de contabilidade,,",
            "812,Início,/,722",
            "825,Clientes,,722",
            "831,Cadastro,/clients,825",
            "835,Relatórios,/clients/reports,825",
            "903,Financeiro,,722",
            "912,Resumo,/fin/summary,903",
            "928,Relatórios,/fin/reports,903"
        )
    )
    sample2.printTree()
    
}

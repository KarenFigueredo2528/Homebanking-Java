const {createApp} = Vue;

const options ={
    data(){
        return{
            clients: [],
            accounts:[],
            loans:[]
        }
    },
    created(){
        this.loadData();
    },
    methods:{
        loadData(){
            axios.get("http://localhost:8080/api/clients/1")
            .then(answer => {
                this.clients = answer.data;
                this.loans = this.clients.loans;
                console.log(this.loans);
                for (const account of this.clients.accounts) {
                    const aux = {
                        id: account.id,
                        number: account.number,
                        creationDate: account.creationDate,
                        balance: account.balance
                    }
                    this.accounts.push(aux)
                }
                console.log(this.accounts);
            }).catch((error) => console.log(error));

        }
    }
}
const app = createApp(options);
app.mount("#app");
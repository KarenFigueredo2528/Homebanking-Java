const {createApp} = Vue;

const options ={
    data(){
        return{
            clients: [],
            accounts:[]
        }
    },
    created(){
        this.loadData();
    },
    methods:{
        loadData(){
            axios.get("http://localhost:8080/api/clients")
            .then(answer => {
                this.clients = answer.data[0];
                console.log(this.clients);
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
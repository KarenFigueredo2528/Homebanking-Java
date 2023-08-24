const { createApp } = Vue;

const options = {
    data() {
        return {
            clients: [],
            accounts: [],
            loans: []
        }
    },
    created() {
        this.loadData();
    },
    methods: {
        loadData() {
            axios.get("http://localhost:8080/api/clients/current")
                .then(answer => {
                    this.clients = answer.data;
                    console.log(this.client);
                    this.loans = this.clients.loans;
                    this.accounts = this.clients.accounts.sort((a, b) => a.id - b.id);
                    console.log(this.loans);
                }).catch((error) => console.log(error));
        },
        logOut(){
            axios.post("/api/logout")
            .then(response =>{
                location.href = "../../index.html"
            })
            .catch(error=> console.log(error.message))
        }
    }
}
const app = createApp(options);
app.mount("#app");
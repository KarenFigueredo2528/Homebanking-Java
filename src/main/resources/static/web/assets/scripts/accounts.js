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
            axios.get("http://localhost:8080/api/clients/1")
                .then(answer => {
                    this.clients = answer.data;
                    this.loans = this.clients.loans;
                    this.accounts = this.clients.accounts.sort((a, b) => a.id - b.id);
                    console.log(this.loans);
                }).catch((error) => console.log(error));

        }
    }
}
const app = createApp(options);
app.mount("#app");